package com.mycompany.smartlibrary;

public class Multimedia extends LibraryResource implements Loanable {
    public Multimedia(String resourceID, String title, String location, int stock) {
        super(resourceID, title, location, stock);
    }

    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 10000;
    }

    @Override
    public void borrowItem() {
        if (!isBorrowed()) {
            setIsBorrowed(true);
            System.out.println("Buku " + getTitle() + " berhasil dipinjam.");
        } else {
            System.out.println("Buku sedang tidak tersedia.");
        }
    }

    @Override
    public void returnItem() {
        setIsBorrowed(false);
        System.out.println("Buku " + getTitle() + " dikembalikan.");
    }
}