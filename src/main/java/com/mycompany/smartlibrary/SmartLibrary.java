package com.mycompany.smartlibrary;

public class SmartLibrary {

    private ConsoleHelper consoleHelper;
    private Shelf bookShelf;

    public SmartLibrary() {
        consoleHelper = new ConsoleHelper();
        bookShelf = new Shelf<Book>();
    }

    public void mainMenu() {
        consoleHelper.printLogo();
        consoleHelper.printOptions();

        int option = consoleHelper.askForInt("Choose option [1..9]: ", 1, 9);
        handleOption(option);
    }

    public void handleOption(int option) {
        switch (option) {
            case 2: {
                addNewLibraryResource();
            }
        }
    }

    public void addNewLibraryResource() {
        String resourceID = consoleHelper.askForString("Input Resource ID", 5, 5);
        String title = consoleHelper.askForString("Input title: ",10,50);
        String location = consoleHelper.askForString("Input location: ",10,50);
        ResourceType resourceType = consoleHelper.askForEnum("Input Resource Type: ", ResourceType.class);

        if (resourceType == ResourceType.BOOK) {
            bookShelf.addItem(new Book(resourceID, title, location));
        } else if (resourceType == ResourceType.JOURNAL) {
            bookShelf.addItem(new Journal(resourceID, title, location));
        } else {
            bookShelf.addItem(new Multimedia(resourceID, title, location));
        }
    }
}
