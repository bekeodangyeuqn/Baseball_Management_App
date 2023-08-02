package com.example.baseballmanagementapp.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    String firstname, lastname, profilepic, username, mail, password, userId;
    List<Team> teams = new ArrayList<>();;
    String dob;

    public User(String firstname, String lastname, String profilepic, String username,
                String mail, String password, String userId, String dob) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.profilepic = profilepic;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.dob = dob;
    }

    public User(){}

    public User(String firstname, String lastname, String username, String mail, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public User(String id, String firstname, String lastname, String username, String mail, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userId = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void addTeam(Team team){
        teams.add(team);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
