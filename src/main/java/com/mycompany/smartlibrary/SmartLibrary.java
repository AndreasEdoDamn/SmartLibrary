package com.mycompany.smartlibrary;
import java.util.ArrayList;

public class SmartLibrary {

    private final ConsoleHelper consoleHelper;
    private final Shelf<Book> bookShelf;
    private final Shelf<Journal> journalShelf;
    private final Shelf<Multimedia> multimediaShelf;
    private ArrayList<LibraryResource> allResources;

    private ArrayList<Member> Members;
    
    public SmartLibrary() {
        consoleHelper = new ConsoleHelper();
        bookShelf = new Shelf<>();
        journalShelf = new Shelf<>();
        multimediaShelf = new Shelf<>();
        allResources = new ArrayList<>();
        
        this.Members = new ArrayList<>();
//        allMembers.add(new Member("M001", "Ani"));
//        allMembers.add(new Member("M002", "Bima"));
    }

    public void mainMenu() {
        boolean isRunning = true;

        while (isRunning) {
            consoleHelper.printLogo();
            consoleHelper.printOptions();

            int option = consoleHelper.askForInt("Choose option [1..9]: ", 1, 9);
            if (option == 9) {
                isRunning = false;
            } else {
                handleOption(option);
            }
        }
    }

    public void handleOption(int option) {
        switch (option) {
            case 1:
                showAllLibraryResource(allResources);
                consoleHelper.pressEnterToContinue();
                break;
            case 2:
                addNewLibraryResource();
                consoleHelper.pressEnterToContinue();
                break;
            case 3:
                updateLibraryResourceStock();
                consoleHelper.pressEnterToContinue();
                break;
            case 4:
                borrowResource(allResources);
                consoleHelper.pressEnterToContinue();
                break;
                
            case 6:
                addMember();
                consoleHelper.pressEnterToContinue();
                break;
            case 7:
                removeMember();
                consoleHelper.pressEnterToContinue();
                break;
            case 8:
                showAllMembers();
                consoleHelper.pressEnterToContinue();
                break;
        }
    }

    public void addNewLibraryResource() {
        String resourceID;
        while (true) {
            resourceID = consoleHelper.askForString("Input Resource ID: ", 1, 5);
            if (isIdExist(resourceID)) {
                System.out.println("Error: ID has already been used! Please use another ID.");
            } else {
                break;
            }
        }

        String title = consoleHelper.askForString("Input title: ",10,50);
        String location = consoleHelper.askForString("Input location: ",10,50);
        int stock = consoleHelper.askForInt("Input stock: ",0,999999999);
        ResourceType resourceType = consoleHelper.askForEnum("Input Resource Type: ", ResourceType.class);

        LibraryResource newItem = null;

        if (resourceType == ResourceType.BOOK) {
            Book book = new Book(resourceID, title, location, stock);
            bookShelf.addItem(book);
            newItem = book;
        } else if (resourceType == ResourceType.JOURNAL) {
            Journal journal = new Journal(resourceID, title, location, stock);
            journalShelf.addItem(journal);
            newItem = journal;
        } else {
            Multimedia multimedia = new Multimedia(resourceID, title, location, stock);
            multimediaShelf.addItem(multimedia);
            newItem = multimedia;
        }

        allResources.add(newItem);
        System.out.println("Success! Item has been added to " + resourceType);
    }

    public void showAllLibraryResource(ArrayList<LibraryResource> items) {
        if (items.isEmpty()) {
            System.out.println("┌────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                       PERPUSTAKAAN SEDANG KOSONG                       │");
            System.out.println("└────────────────────────────────────────────────────────────────────────┘");
            
            return;
        }

        String format = "│ %-8s │ %-12s │ %-30s │ %-15s │ %-6s │%n";

        String lineTop    = "┌──────────┬──────────────┬────────────────────────────────┬─────────────────┬────────┐";
        String lineMiddle = "├──────────┼──────────────┼────────────────────────────────┼─────────────────┼────────┤";
        String lineBottom = "└──────────┴──────────────┴────────────────────────────────┴─────────────────┴────────┘";

        System.out.println(lineTop);
        System.out.printf(format, "ID", "Type", "Title", "Location", "Stok");
        System.out.println(lineMiddle);

        for (LibraryResource item : items) {
            String type = item.getClass().getSimpleName();

            String displayTitle = item.getTitle();
            if (displayTitle.length() > 28) {
                displayTitle = displayTitle.substring(0, 25) + "...";
            }

            int stock = 0;
            if (item instanceof Book) {
                stock = ((Book) item).getStock();
            } else if (item instanceof Journal) {
                stock = ((Journal) item).getStock();
            } else if (item instanceof Multimedia) {
                stock = ((Multimedia) item).getStock();
            }

            System.out.printf(format,
                    item.getResourceID(),
                    type,
                    displayTitle,
                    item.getLocation(),
                    stock
            );
        }

        System.out.println(lineBottom);
        
    }

    public void updateLibraryResourceStock() {
        String id = consoleHelper.askForString("Input Resource ID: ", 1, 5);

        LibraryResource item = findResourceById(id);
        if (item == null) {
            consoleHelper.printError("Item with ID " + id + " not found!");
            consoleHelper.pressEnterToContinue();
            return;
        }

        System.out.println("Item found: " + item.getTitle());
        System.out.println("Current stock: " + item.getStock());

        int newStock = consoleHelper.askForInt("Input new stock: ", 0, 1000);

        item.setStock(newStock);
        consoleHelper.printSuccess("Stok successfully updated: " + newStock);
    }
    
    public void addMember() {
        String memberID;
        while (true) {
            memberID = consoleHelper.askForString("Input Member ID: ", 1, 5);
            if (isMemberIdExist(memberID)) {
                System.out.println("Error: ID has already been used! Please use another ID.");
            } else {
                break;
            }
        }

        String name = consoleHelper.askForString("Input name: ",1,50);
        Members.add(new Member(name, memberID));
        consoleHelper.printSuccess("Member have been successfully added!");
    }

    public void removeMember() {
        String memberID = consoleHelper.askForString("Input Member ID: ",1,50);
        Member member = findMemberById(memberID);
        if (member == null) {
            consoleHelper.printError("Member with ID " + memberID + " not found!");
            return;
        }

        Members.remove(member);
        consoleHelper.printSuccess("Member have been successfully removed!");
    }

    public void showAllMembers() {
        if (Members.isEmpty()) {
            System.out.println("┌──────────────────────────────────────────────────────┐");
            System.out.println("│                  NO MEMBERS REGISTERED               │");
            System.out.println("└──────────────────────────────────────────────────────┘");
            return;
        }

        String format = "│ %-8s │ %-30s │ %-5s │%n";

        String lineTop    = "┌──────────┬────────────────────────────────┬───────┐";
        String lineMiddle = "├──────────┼────────────────────────────────┼───────┤";
        String lineBottom = "└──────────┴────────────────────────────────┴───────┘";

        System.out.println(lineTop);
        System.out.printf(format, "ID", "Name", "Loans");
        System.out.println(lineMiddle);

        for (Member member : Members) {
            String displayName = member.getName();
            if (displayName.length() > 28) {
                displayName = displayName.substring(0, 25) + "...";
            }

            int loanCount =  member.getBorrowCount();//member.getBorrowedCount();

            System.out.printf(format,
                    member.getMemberID(),
                    displayName,
                    loanCount
            );
        }

        System.out.println(lineBottom);
    }
    
    public void showMemberBorrowedBooks() {
        String memberID = consoleHelper.askForString("Input Member ID: ", 1, 5);
        Member member = findMemberById(memberID);
        
        if (member == null) {
            consoleHelper.printError("Member with ID " + memberID + " not found!");
            consoleHelper.pressEnterToContinue();
            return;
        }

        LibraryResource[] borrowedItems = member.getBorrowedItems();
        
        System.out.println("\n--- Item Dipinjam oleh: " + member.getName() + " ---");
        
        // Hitung jumlah item yang dipinjam
        int count = 0;
        for (LibraryResource item : borrowedItems) {
            if (item != null) {
                count++;
            }
        }
        
        if (count == 0) {
            System.out.println("┌───────────────────────────────────────────────────┐");
            System.out.println("│              Anggota tidak meminjam item          │");
            System.out.println("└───────────────────────────────────────────────────┘");
            consoleHelper.pressEnterToContinue();
            return;
        }
        
        // Tampilkan tabel item yang dipinjam
        String format = "│ %-8s │ %-12s │ %-30s │%n";

        String lineTop    = "┌──────────┬──────────────┬────────────────────────────────┐";
        String lineMiddle = "├──────────┼──────────────┼────────────────────────────────┤";
        String lineBottom = "└──────────┴──────────────┴────────────────────────────────┘";

        System.out.println(lineTop);
        System.out.printf(format, "ID", "Type", "Title");
        System.out.println(lineMiddle);
        
        for (LibraryResource item : borrowedItems) {
            if (item != null) {
                 String type = item.getClass().getSimpleName();
                 String displayTitle = item.getTitle();
                 if (displayTitle.length() > 28) {
                     displayTitle = displayTitle.substring(0, 25) + "...";
                 }
                System.out.printf(format,
                    item.getResourceID(),
                    type,
                    displayTitle
                );
            }
        }
        System.out.println(lineBottom);
        
        consoleHelper.pressEnterToContinue();
    }

    private LibraryResource findResourceById(String id) {
        for (LibraryResource item : allResources) {
            if (item.getResourceID().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }
    private Member findMemberById(String id) {
        for (Member member : Members) {
            if (member.getMemberID().equalsIgnoreCase(id)) {
                return member;
            }
        }
        return null;
    }

    private boolean isIdExist(String id) {
        for (LibraryResource item : allResources) {
            if (item.getResourceID().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isMemberIdExist(String id) {
        for (Member member : Members) {
            if (member.getMemberID().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
    
    private void borrowResource(ArrayList<LibraryResource> items){
        showAllLibraryResource(allResources);
        if(!items.isEmpty()){
            String id = consoleHelper.askForString("Input Resource ID: ", 1, 5);

            LibraryResource item = findResourceById(id);
            if (item == null) {
                consoleHelper.printError("Item with ID " + id + " not found!");
                return;
            }
            
            String memberID = consoleHelper.askForString("Input Member ID: ",1,50);
            Member member = findMemberById(memberID);
            if (member == null) {
                consoleHelper.printError("Member with ID " + memberID + " not found!");
                return;
            }
            
            if(!member.canBorrow()){
                consoleHelper.printError("Your borrow quota is full please return a book first");
                return;
            }
            
            Loanable loanableItem = (Loanable) item;
            loanableItem.borrowItem();
            item.setStock(item.getStock() - 1);
            member.borrowItem(item);
            

            
            
        }
    }
    
    private void returnResource() {
        String memberID = consoleHelper.askForString("Input Member ID: ", 1, 5);
        Member member = findMemberById(memberID);
        
        if (member == null) {
            consoleHelper.printError("Member with ID " + memberID + " not found!");
            consoleHelper.pressEnterToContinue();
            return;
        }

        String resourceID = consoleHelper.askForString("Input Resource ID to return: ", 1, 5);
        
        // 1. Hapus item dari daftar pinjaman member
        // member.returnItem() akan mengembalikan objek LibraryResource jika ditemukan di pinjaman member
        LibraryResource item = member.returnItem(resourceID);
        
        if (item == null) {
            consoleHelper.printError("Error: Resource ID " + resourceID + " not found in " + member.getName() + "'s borrowed list.");
            consoleHelper.pressEnterToContinue();
            return;
        }
        
        // 2. Lakukan logika pengembalian di sisi LibraryResource
        
        // Casting ke Loanable (karena semua subkelas mengimplementasikannya)
        Loanable loanableItem = (Loanable) item;
        loanableItem.returnItem(); // Panggil returnItem() (menyetel isBorrowed=false)
            
        // 3. Tambah stok di perpustakaan
        item.setStock(item.getStock() + 1);

        // 4. Hitung Denda
        int daysLate = consoleHelper.askForInt("Input days late (0 if on time): ", 0, 365);
        
        // calculateFine() akan memanggil implementasi spesifik (Book/Journal/Multimedia)
        double fine = item.calculateFine(daysLate); 

        consoleHelper.printSuccess("Resource " + item.getTitle() + " has been successfully returned.");
        System.out.println("Stok baru: " + item.getStock());
        
        if (fine > 0) {
            System.out.println("⚠️ Fine for " + daysLate + " days late: Rp " + (int)fine);
        } else {
            System.out.println("✅ On time return. No fine assessed.");
        }
        
        consoleHelper.pressEnterToContinue();
    }

}
