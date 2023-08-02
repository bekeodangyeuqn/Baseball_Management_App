package com.example.baseballmanagementapp.models;

public class Manager extends User{


    public Manager(String firstname, String lastname, String profilepic, String username, String mail, String password, String userId, String dob) {
        super(firstname, lastname, profilepic, username, mail, password, userId, dob);
    }

    public Manager() {

    }

    public Manager(String firstname, String lastname, String username, String mail, String password) {
        super(firstname, lastname, username, mail, password);
    }
}
