package com.example.baseballmanagementapp.models;

public class Game extends Event{
    String yourTeam ;
    String oppTeam;

    int yourScore, oppScore, yourHit, oppHit, yourError, oppErr;

    public Game(){

    }

    public Game(String teamId, String name, String eventId, String location, String timeStart, String timeEnd,  String yourTeam, String oppTeam) {
        super(teamId, name, eventId, location, timeStart, timeEnd);
        this.oppTeam = oppTeam;
        this.yourTeam = yourTeam;
    }

    public String getYourTeam() {
        return yourTeam;
    }

    public void setYourTeam(String yourTeam) {
        this.yourTeam = yourTeam;
    }

    public String getOppTeam() {
        return oppTeam;
    }

    public void setOppTeam(String oppTeam) {
        this.oppTeam = oppTeam;
    }
}
