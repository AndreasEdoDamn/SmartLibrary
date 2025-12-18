package com.mycompany.smartlibrary;

public class Book extends LibraryResource implements Loanable {

    //===========================================================================================================================//

    public Book(String resourceID, String title, String location, int stock) {
        super(resourceID, title, location, stock);
    }

    //===========================================================================================================================//

    @Override
    public double calculateFine(int daysLate) {
        return daysLate * Constants.FINE_AMOUNT.BOOK;
    }

    @Override
    public void borrowItem() {
        if (getStock() > 0) {
            setIsBorrowed(true);
            System.out.println("Item \"" + getTitle() + "\" successfully borrowed.");
        } else {
            System.out.println("Item is not available.");
        }
    }

    @Override
    public void returnItem() {
        System.out.println("Item \"" + getTitle() + "\" has been returned.");
    }

    //===========================================================================================================================//

}