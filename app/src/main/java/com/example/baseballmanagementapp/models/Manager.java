package com.example.baseballmanagementapp.models;

import java.time.LocalDate;

public class Manager extends User{


    public Manager(String firstname, String lastname, String profilepic, String username, String mail, String password, String userId, LocalDate dob) {
        super(firstname, lastname, profilepic, username, mail, password, userId, dob);
    }

    public Manager() {

    }

    public Manager(String firstname, String lastname, String username, String mail, String password) {
        super(firstname, lastname, username, mail, password);
    }
}
