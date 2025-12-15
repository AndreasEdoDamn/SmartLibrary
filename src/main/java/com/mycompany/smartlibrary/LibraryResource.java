package com.mycompany.smartlibrary;

public abstract class LibraryResource {
    private String resourceID;
    private String title;
    private String location;
    private int stock;

    public LibraryResource(String resourceID, String title, String location) {
        this.resourceID = resourceID;
        this.title = title;
        this.location = location;
    }

    public String getTitle() { return title; }
    public String getResourceID() { return resourceID; }

    public abstract double calculateFine(int daysLate);
}
