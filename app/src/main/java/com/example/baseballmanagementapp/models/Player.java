package com.example.baseballmanagementapp.models;

public class Player extends User{
    String avg, firstPos, secondPos;
    int hr, rbi, sb, so, height, weight;
    Team playerTeam;

    public Player(String firstname, String lastname, String profilepic, String username, String mail, String password, String userId,String dob, String firstPos, String secondPos, int height, int weight, Team team) {
        super(firstname, lastname, profilepic, username, mail, password, userId, dob);
        this.firstPos = firstPos;
        this.secondPos = secondPos;
        this.height = height;
        this.weight = weight;
        this.playerTeam = team;
    }

    public Player(String firstPos, String secondPos, int height, int weight, Team team) {
        this.firstPos = firstPos;
        this.secondPos = secondPos;
        this.height = height;
        this.weight = weight;
        this.playerTeam = team;
    }

    public Player(String firstname, String lastname, String username, String mail, String password, String firstPos, String secondPos, int height, int weight, Team team) {
        super(firstname, lastname, username, mail, password);
        this.firstPos = firstPos;
        this.secondPos = secondPos;
        this.height = height;
        this.weight = weight;
        this.playerTeam = team;
    }

    public Player(String firstname, String lastname, String profilepic, String username, String mail, String password, String userId, String dob, String avg, String firstPos, String secondPos, int hr, int rbi, int sb, int so, int height, int weight, Team team) {
        super(firstname, lastname, profilepic, username, mail, password, userId, dob);
        this.avg = avg;
        this.firstPos = firstPos;
        this.secondPos = secondPos;
        this.hr = hr;
        this.rbi = rbi;
        this.sb = sb;
        this.so = so;
        this.height = height;
        this.weight = weight;
        this.playerTeam = team;
    }

    public Player(String avg, String firstPos, String secondPos, int hr, int rbi, int sb, int so, int height, int weight, Team team) {
        this.avg = avg;
        this.firstPos = firstPos;
        this.secondPos = secondPos;
        this.hr = hr;
        this.rbi = rbi;
        this.sb = sb;
        this.so = so;
        this.height = height;
        this.weight = weight;
        this.playerTeam = team;
    }

    public Player(String firstname, String lastname, String username, String mail, String password, String avg, String firstPos, String secondPos, int hr, int rbi, int sb, int so, int height, int weight, Team team) {
        super(firstname, lastname, username, mail, password);
        this.avg = avg;
        this.firstPos = firstPos;
        this.secondPos = secondPos;
        this.hr = hr;
        this.rbi = rbi;
        this.sb = sb;
        this.so = so;
        this.height = height;
        this.weight = weight;
        this.playerTeam = team;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getFirstPos() {
        return firstPos;
    }

    public void setFirstPos(String firstPos) {
        this.firstPos = firstPos;
    }

    public String getSecondPos() {
        return secondPos;
    }

    public void setSecondPos(String secondPos) {
        this.secondPos = secondPos;
    }

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public int getRbi() {
        return rbi;
    }

    public void setRbi(int rbi) {
        this.rbi = rbi;
    }

    public int getSb() {
        return sb;
    }

    public void setSb(int sb) {
        this.sb = sb;
    }

    public int getSo() {
        return so;
    }

    public void setSo(int so) {
        this.so = so;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Team getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(Team playerTeam) {
        this.playerTeam = playerTeam;
    }
}
