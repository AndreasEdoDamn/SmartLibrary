package com.mycompany.smartlibrary;

import java.util.ArrayList;

public class Shelf<T extends LibraryResource> {

    //===========================================================================================================================//

    private final ArrayList<T> items;

    //===========================================================================================================================//

    public Shelf() {
        this.items = new ArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
    }

    //===========================================================================================================================//

}