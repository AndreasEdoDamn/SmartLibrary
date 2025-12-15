package com.mycompany.smartlibrary;

public abstract class LibraryResource {
    private String resourceID;
    private String title;
    private String location;
    private int stock;
    private boolean isBorrowed;

    public LibraryResource(String resourceID, String title, String location, int stock) {
        this.resourceID = resourceID;
        this.title = title;
        this.location = location;
        this.stock = stock;
        this.isBorrowed = false;
    }

    public String getTitle() { return title; }
    public String getResourceID() { return resourceID; }
    public String getLocation() { return location; }
    public int getStock() { return stock; }
    public void setStock(int newStock) {stock = newStock;}
    public boolean isBorrowed() {return isBorrowed;}
    public void setIsBorrowed(boolean newVal) {isBorrowed = newVal;}

    public abstract double calculateFine(int daysLate);
}
