package com.mycompany.smartlibrary;

import java.util.ArrayList;

public class Shelf<T extends LibraryResource> {
    private String shelfCategory;
    private ArrayList<T> items;

    public Shelf() {
        this.shelfCategory = shelfCategory;
        this.items = new ArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
    }
}