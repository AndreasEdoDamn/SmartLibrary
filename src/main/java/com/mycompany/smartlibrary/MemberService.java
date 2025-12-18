package com.mycompany.smartlibrary;
import java.util.ArrayList;

public class MemberService {

    //===========================================================================================================================//

    private final ArrayList<Member> members;

    //===========================================================================================================================//

    public MemberService() {
        members = new ArrayList<>();
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    //===========================================================================================================================//

    public void addMember(ConsoleHelper consoleHelper) {
        ConsoleHelper.printOneLineNotification("Adding New Member");
        String memberID = IDService.generateMemberID(members);
        String name = consoleHelper.askForString("Input name",1,50);
        Member memberObject = new Member(name, memberID);
        members.add(memberObject);
        ConsoleHelper.printSuccess("Member have been successfully added!");
    }

    public void removeMember(ConsoleHelper consoleHelper) {
        ConsoleHelper.printOneLineNotification("Removing Members");
        showAllMembers();
        if (members.isEmpty()) return;

        String memberID = consoleHelper.askForString("Input Member ID",1,50);
        Member member = IDService.findMemberById(members, memberID);
        if (member == null) {
            ConsoleHelper.printError("Member with ID " + memberID + " not found!");
            return;
        }
        for(LoanDetail item: member.getBorrowedItems()){
            if(item == null) continue;
            item.getResource().setStock(item.getResource().getStock() + 1);
        }
        members.remove(member);
        ConsoleHelper.printSuccess("Member have been successfully removed!");
    }

    public void showAllMembers() {
        ConsoleHelper.printOneLineNotification("Showing All Members");
        if (members.isEmpty()) {
            ConsoleHelper.printNotification("NO MEMBERS REGISTERED");
            return;
        }

        String format = "│ %-8s │ %-30s │ %-5s │%n";

        TableHelper.printTableHeader(format,
                "ID", "Name", "Loans"
        );

        for (Member member : members) {
            TableHelper.printTableRow(format,
                    member.getMemberID(),
                    member.getDisplayName(),
                    member.getBorrowCount()
            );
        }

        TableHelper.printBorder(format, BorderType.BOTTOM);
    }

    //===========================================================================================================================//

}
