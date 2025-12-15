package com.mycompany.smartlibrary;

import java.util.ArrayList;
import java.util.Arrays;


public class Member {
    private String memberID;
    private String name;
    private LibraryResource[] borrowedItems;
    private int borrowQuota = 3;
    
    public Member(String name, String memberID){
        this.memberID = memberID;
        this.name = name;
        this.borrowedItems = new LibraryResource[borrowQuota];
    }
    
    public String getMemberID(){
        return memberID;
    }
    public String getName(){
        return name;
    }
    public LibraryResource[] getBorrowedItems(){
        return borrowedItems;
    }
    
    public boolean canBorrow(){
        for(LibraryResource item: borrowedItems){
            if(item == null){
                return true;
            }
        }
        return false;
    }
    
    public boolean borrowItem(LibraryResource item){
        if(!canBorrow()){
            return false;
        }
        for(int i=0;i<borrowedItems.length;i++){
            if(borrowedItems[i] == null){
                borrowedItems[i] = item;
                this.borrowQuota--;
                return true;
            }
        }
        return false;
    }
    
    public LibraryResource returnItem(String resourceID){
        for(int i=0;i<borrowedItems.length;i++){
            LibraryResource item = borrowedItems[i];
            if(item != null && item.getResourceID().equalsIgnoreCase(resourceID)){
                borrowedItems[i] = null;
                this.borrowQuota++;
                return item;
            }
            
        }
        return null;
    }
    
    public int getBorrowCount(){
        int count = 0;
        for(LibraryResource item: borrowedItems){
            if(item!=null){
                count++;
            }
        }
        return count;
    }
}