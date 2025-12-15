package com.mycompany.smartlibrary;

public class Multimedia extends LibraryResource implements Loanable {
    private boolean isBorrowed;

    public Multimedia(String resourceID, String title, String location) {
        super(resourceID, title, location);
        this.isBorrowed = false;
    }

    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 10000;
    }

    @Override
    public void borrowItem() {
        if (!isBorrowed) {
            isBorrowed = true;
            System.out.println("Buku " + getTitle() + " berhasil dipinjam.");
        } else {
            System.out.println("Buku sedang tidak tersedia.");
        }
    }

    @Override
    public void returnItem() {
        isBorrowed = false;
        System.out.println("Buku " + getTitle() + " dikembalikan.");
    }
}