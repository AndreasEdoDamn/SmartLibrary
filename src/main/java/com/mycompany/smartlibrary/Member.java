package com.mycompany.smartlibrary;

public class Member {

    //===========================================================================================================================//

    private String memberID;
    private String name;
    private final LoanDetail[] borrowedItems;
    private final int MAX_BORROW_LIMIT = 3;

    //===========================================================================================================================//

    public Member(String name, String memberID){
        this.memberID = memberID;
        this.name = name;
        // Array dibuat dengan ukuran tetap (3)
        this.borrowedItems = new LoanDetail[MAX_BORROW_LIMIT];
    }

    //===========================================================================================================================//

    public String getDisplayName() {
        if (name == null) return "";
        String displayName = name;
        if (name.length() > 28) {
            displayName = name.substring(0, 25) + "...";
        }
        return displayName;
    }

    // Getter
    public String getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public LoanDetail[] getBorrowedItems() {
        return borrowedItems;
    }

    public int getBorrowQuota() {
        return MAX_BORROW_LIMIT - getBorrowCount();
    }

    // Setter
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean canBorrow(){
        return getBorrowCount() < MAX_BORROW_LIMIT;
    }

    public void borrowItem(LibraryResource item){
        if(!canBorrow()){
            System.out.println("Quota penuh, tidak bisa meminjam.");
            return;
        }

        for (int i = 0; i < borrowedItems.length; i++) {
            if (borrowedItems[i] == null) {
                LoanDetail newLoan = new LoanDetail(item);
                newLoan.setResource(item);

                borrowedItems[i] = newLoan;
                return;
            }
        }
    }

    public LoanDetail returnItem(String resourceID){
        for(int i = 0; i < borrowedItems.length; i++){
            if(borrowedItems[i] != null) {
                LoanDetail item = borrowedItems[i];

                if(item != null && item.getResource().getResourceID().equalsIgnoreCase(resourceID)){
                    borrowedItems[i] = null;
                    return item;
                }
            }
        }
        return null;
    }

    public int getBorrowCount(){
        int count = 0;
        for(LoanDetail item: borrowedItems){
            if(item != null){
                count++;
            }
        }
        return count;
    }
}