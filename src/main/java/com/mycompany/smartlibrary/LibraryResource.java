package com.mycompany.smartlibrary;

public abstract class LibraryResource {

    //===========================================================================================================================//

    private String resourceID;
    private String title;
    private String location;
    private int stock;
    private boolean isBorrowed;

    //===========================================================================================================================//

    public LibraryResource(String resourceID, String title, String location, int stock) {
        this.resourceID = resourceID;
        this.title = title;
        this.location = location;
        this.stock = stock;
        this.isBorrowed = false;
    }

    //===========================================================================================================================//

    public String getDisplayTitle() {
        String displayTitle = title;
        if (displayTitle.length() > 28) {
            displayTitle = displayTitle.substring(0, 25) + "...";
        }
        return displayTitle;
    }

    public String getDisplayLocation() {
        String location = this.location;
        if (location.length() > 13) {
            location = location.substring(0, 10) + "...";
        }
        return location;
    }

    //===========================================================================================================================//

    public String getResourceID() {
        return resourceID;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public int getStock() {
        return stock;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    //===========================================================================================================================//

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStock(int newStock) {
        this.stock = newStock;
    }

    public void setIsBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public abstract double calculateFine(int daysLate);

    //===========================================================================================================================//

}
