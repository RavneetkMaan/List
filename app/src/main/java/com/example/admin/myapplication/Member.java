package com.example.admin.myapplication;

import android.widget.Button;

/**
 * Created by admin on 4/27/2017.
 */

public class Member {

    private String name, emailId;
    private int memberID;


    public Member(String name, String emailId, int memberID) {
        this.name = name;
        this.emailId = emailId;
        this.memberID = memberID;

    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }
}
