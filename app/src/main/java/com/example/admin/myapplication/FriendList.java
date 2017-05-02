package com.example.admin.myapplication;

/**
 * Created by admin on 4/27/2017.
 */

public class FriendList {

    String name;
    private int memberId;


    public FriendList(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
