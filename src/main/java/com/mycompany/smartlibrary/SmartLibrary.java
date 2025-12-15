package com.mycompany.smartlibrary;
import java.util.ArrayList;

public class SmartLibrary {

    private final ConsoleHelper consoleHelper;
    private final Shelf<Book> bookShelf;
    private final Shelf<Journal> journalShelf;
    private final Shelf<Multimedia> multimediaShelf;
    private ArrayList<LibraryResource> allResources;

    public SmartLibrary() {
        consoleHelper = new ConsoleHelper();
        bookShelf = new Shelf<>();
        journalShelf = new Shelf<>();
        multimediaShelf = new Shelf<>();
        allResources = new ArrayList<>();
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
                break;
            case 2:
                addNewLibraryResource();
                break;
            case 3:
                updateLibraryResourceStock();
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
        consoleHelper.pressEnterToContinue();
    }

    public void showAllLibraryResource(ArrayList<LibraryResource> items) {
        if (items.isEmpty()) {
            System.out.println("┌────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                       PERPUSTAKAAN SEDANG KOSONG                       │");
            System.out.println("└────────────────────────────────────────────────────────────────────────┘");
            consoleHelper.pressEnterToContinue();
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
        consoleHelper.pressEnterToContinue();
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

    private boolean isIdExist(String id) {
        for (LibraryResource item : allResources) {
            if (item.getResourceID().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
}
