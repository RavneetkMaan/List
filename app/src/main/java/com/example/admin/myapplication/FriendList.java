package com.example.admin.myapplication;

/**
 * Created by admin on 4/27/2017.
 */

public class FriendList {

    String name,emailId;

    public FriendList(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
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
}
