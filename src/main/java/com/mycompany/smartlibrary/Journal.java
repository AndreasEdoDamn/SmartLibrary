package com.mycompany.smartlibrary;

public class Journal extends LibraryResource implements Loanable {
    public Journal(String resourceID, String title, String location, int stock) {
        super(resourceID, title, location, stock);
    }

    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 5000;
    }

    @Override
    public void borrowItem() {
        if (getStock() > 0) {
            setIsBorrowed(true);
            System.out.println("Item \"" + getTitle() + "\" successfully borrowed.");
        } else {
            System.out.println("Item does not exist.");
        }
    }

    @Override
    public void returnItem() {
        setIsBorrowed(false);
        System.out.println("Item \"" + getTitle() + "\" has been returned.");
    }
}