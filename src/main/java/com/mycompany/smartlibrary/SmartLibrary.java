package com.mycompany.smartlibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmartLibrary {

    //===========================================================================================================================//

    private final MemberService memberService;
    private final ConsoleHelper consoleHelper;
    private final Map<String, Shelf<LibraryResource>> shelves;
    private final ArrayList<LibraryResource> resources;

    //===========================================================================================================================//

    public SmartLibrary() {
        this.memberService = new MemberService();
        this.consoleHelper = new ConsoleHelper();
        this.resources = new ArrayList<>();
        this.shelves = new HashMap<>();

        this.shelves.put("Book", new Shelf<LibraryResource>());
        this.shelves.put("Journal", new Shelf<LibraryResource>());
        this.shelves.put("Multimedia", new Shelf<LibraryResource>());

        initializeData();
    }

    private void initializeData() {
        memberService.getMembers().add(new Member("Dicco", "M001"));
        Book book = new Book("R001", "Example Book", "Example Location", 100);
        resources.add(book);
        shelves.get("Book").addItem(book);
    }

    //===========================================================================================================================//

    public void mainMenu() {
        boolean isRunning = true;

        while (isRunning) {
            ConsoleHelper.clearScreen();
            ConsoleHelper.printLogo();
            ConsoleHelper.printOptions();

            int option = consoleHelper.askForInt("Choose option ", 1, 9);
            if (option == 9) {
                isRunning = false;
            } else {
                handleOption(option);
            }
        }
    }

    public void handleOption(int option) {
        ConsoleHelper.clearScreen();
        switch (option) {
            case 1:
                showAllLibraryResource();
                break;
            case 2:
                addNewLibraryResource();
                break;
            case 3:
                updateLibraryResourceStock();
                break;
            case 4:
                borrowResource();
                break;
            case 5:
                returnResource();
                break;
            case 6:
                memberService.addMember(consoleHelper);
                break;
            case 7:
                memberService.removeMember(consoleHelper);
                break;
            case 8:
                memberService.showAllMembers();
                break;
        }

        consoleHelper.pressEnterToContinue();
    }

    //===========================================================================================================================//

    public void addNewLibraryResource() {
        ConsoleHelper.printOneLineNotification("Adding New Library Resource");
        String title = consoleHelper.askForString("\nInput title", 10, 100);
        String location = consoleHelper.askForString("Input location", 10, 100);
        int stock = consoleHelper.askForIntBiggerThan("Input stock", 0);
        ResourceType resourceType = consoleHelper.askForEnum("\nInput Resource Type", ResourceType.class);

        String resourceID = IDService.generateLibraryResourceID(resources);
        LibraryResource newItem = null;

        if (resourceType == ResourceType.Book) {
            newItem = new Book(resourceID, title, location, stock);
        } else if (resourceType == ResourceType.Journal) {
            newItem = new Journal(resourceID, title, location, stock);
        } else if (resourceType == ResourceType.Multimedia) {
            newItem = new Multimedia(resourceID, title, location, stock);
        }

        shelves.get(resourceType.name()).addItem(newItem);
        resources.add(newItem);
        ConsoleHelper.printSuccess("Item has been added to " + resourceType.name());
    }

    public void showAllLibraryResource() {
        ConsoleHelper.printOneLineNotification("Showing All Library Resources");
        if (resources.isEmpty()) {
            ConsoleHelper.printNotification("LIBRARY IS EMPTY");
            return;
        }

        String format = "│ %-8s │ %-12s │ %-30s │ %-15s │ %-6s │%n";

        TableHelper.printTableHeader(format,
                "ID", "Type", "Title", "Location", "Stok"
        );

        for (LibraryResource item : resources) {
            TableHelper.printTableRow(format,
                    item.getResourceID(),
                    item.getClass().getSimpleName(),
                    item.getDisplayTitle(),
                    item.getDisplayLocation(),
                    item.getStock()
            );
        }

        TableHelper.printBorder(format, BorderType.BOTTOM);
    }

    public void updateLibraryResourceStock() {
        ConsoleHelper.printOneLineNotification("Updating Library Resource Stock");
        showAllLibraryResource();
        if (resources.isEmpty()) return;


        String libraryResourceID = consoleHelper.askForString("Input Resource ID", 1, 5);
        LibraryResource item = IDService.findLibraryResourceById(resources, libraryResourceID);
        if (item == null) {
            ConsoleHelper.printError("Item with ID " + libraryResourceID + " not found!");
            return;
        }

        int newStock = consoleHelper.askForIntBiggerThan("Input new stock", 0);
        item.setStock(newStock);
        ConsoleHelper.printSuccess("Stok successfully updated: " + newStock);
    }

    //===========================================================================================================================//

    public void showMemberBorrowedBooks(Member member) {
        LoanDetail[] borrowedItems = member.getBorrowedItems();
        ConsoleHelper.printOneLineNotification("Items Borrowed by:");

        if (member.getBorrowCount() == 0) {
            ConsoleHelper.printNotification("MEMBER HAS NOT BORROWED ANY ITEM");
            return;
        }

        String format = "│ %-8s │ %-12s │ %-30s │%n";
        TableHelper.printTableHeader(format,
                "ID", "Type", "Title"
        );

        for (LoanDetail item : borrowedItems) {
            if (item == null) continue;
            TableHelper.printTableRow(format,
                    item.getResource().getResourceID(),
                    item.getClass().getSimpleName(),
                    item.getResource().getDisplayTitle()
            );
        }

        TableHelper.printBorder(format, BorderType.BOTTOM);
    }

    private void borrowResource() {
        showAllLibraryResource();
        if (resources.isEmpty()) return;

        String libraryResourceID = consoleHelper.askForString("Input Resource ID ", 1, 5);
        LibraryResource item = IDService.findLibraryResourceById(resources, libraryResourceID);
        if (item == null) {
            ConsoleHelper.printError("Item with ID " + libraryResourceID + " not found!");
            return;
        }

        System.out.println();
        memberService.showAllMembers();
        if (memberService.getMembers().isEmpty()) return;
        String memberID = consoleHelper.askForString("Input Member ID: ", 1, 50);
        Member member = IDService.findMemberById(memberService.getMembers(), memberID);
        if (member == null) {
            ConsoleHelper.printError("Member with ID " + memberID + " not found!");
            return;
        }

        if (!member.canBorrow()) {
            ConsoleHelper.printError("Your borrow quota is full please return a book first");
            return;
        }

        Loanable loanableItem = (Loanable) item;
        loanableItem.borrowItem();
        item.setStock(item.getStock() - 1);
        member.borrowItem(item);
    }

    private void returnResource() {
        ConsoleHelper.printOneLineNotification("Returning Library Resource");
        memberService.showAllMembers();
        if (memberService.getMembers().isEmpty()) return;

        String memberID = consoleHelper.askForString("Input Member ID: ", 1, 5);
        Member member = IDService.findMemberById(memberService.getMembers(), memberID);

        if (member == null) {
            ConsoleHelper.printError("Member with ID " + memberID + " not found!");
            consoleHelper.pressEnterToContinue();
            return;
        }

        System.out.println();
        showMemberBorrowedBooks(member);
        if (member.getBorrowCount() == 0) return;

        String resourceID = consoleHelper.askForString("Input Resource ID to return: ", 1, 5);
        LoanDetail loanDetail = member.returnItem(resourceID);
        LibraryResource item = loanDetail.getResource();
        if (item == null) {
            ConsoleHelper.printError("Resource ID " + resourceID + " not found in " + member.getName() + "'s borrowed list.");
            return;
        }

        Loanable loanableItem = (Loanable) item;
        loanableItem.returnItem();
        item.setStock(item.getStock() + 1);

        double fine = item.calculateFine((int) loanDetail.getDaysLate());

        ConsoleHelper.printSuccess("Resource " + item.getTitle() + " has been successfully returned.");
        System.out.println("Stock: " + item.getStock());

        if (fine > 0) {
            System.out.println("Fine for " + (int) loanDetail.getDaysLate() + " days late: Rp " + (int)fine);
        } else {
            System.out.println("On time return. No fine assessed.");
        }
    }

    //===========================================================================================================================//

}
