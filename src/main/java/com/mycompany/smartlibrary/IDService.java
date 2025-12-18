package com.mycompany.smartlibrary;
import java.util.ArrayList;

public class IDService {

    //===========================================================================================================================//

    public static String generateMemberID(ArrayList<Member> members) {
        int nextIdNumber = members.size() + 1;
        return  String.format("M%03d", nextIdNumber);
    }

    public static String generateLibraryResourceID(ArrayList<LibraryResource> resources) {
        int nextIdNumber = resources.size() + 1;
        return  String.format("R%03d", nextIdNumber);
    }

    //===========================================================================================================================//

    public static boolean isLibraryResourceIdExist(ArrayList<LibraryResource> resources, String id) {
        for (LibraryResource item : resources) {
            if (item.getResourceID().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMemberIdExist(ArrayList<Member> members, String id) {
        for (Member member : members) {
            if (member.getMemberID().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    //===========================================================================================================================//

    public static LibraryResource findLibraryResourceById(ArrayList<LibraryResource> resources, String id) {
        for (LibraryResource item : resources) {
            if (item.getResourceID().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    public static Member findMemberById(ArrayList<Member> members, String id) {
        for (Member member : members) {
            if (member.getMemberID().equalsIgnoreCase(id)) {
                return member;
            }
        }
        return null;
    }

    //===========================================================================================================================//
}
