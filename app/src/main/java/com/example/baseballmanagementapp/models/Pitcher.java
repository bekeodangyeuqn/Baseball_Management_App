package com.example.baseballmanagementapp.models;

import java.util.ArrayList;
import java.util.List;

public class Pitcher extends Player{
    int gameStart, win, lose, pitchSo, bb, hbp, save, hold;
    String era, inningPitched;
    List<String> pitchType = new ArrayList<String>();

    public Pitcher(String firstname, String lastname, String profilepic, String username, String mail, String password, String userId, String dob, String firstPos, String secondPos, int height, int weight, Team team, int gameStart, int win, int lose, int pitchSo, int bb, int hbp, int save, int hold, String era, String inningPitched, List<String> pitchType) {
        super(firstname, lastname, profilepic, username, mail, password, userId, dob, firstPos, secondPos, height, weight, team);
        this.gameStart = gameStart;
        this.win = win;
        this.lose = lose;
        this.pitchSo = pitchSo;
        this.bb = bb;
        this.hbp = hbp;
        this.save = save;
        this.hold = hold;
        this.era = era;
        this.inningPitched = inningPitched;
        this.pitchType = pitchType;
    }

    public Pitcher(String firstPos, String secondPos, int height, int weight, Team team, int gameStart, int win, int lose, int pitchSo, int bb, int hbp, int save, int hold, String era, String inningPitched, List<String> pitchType) {
        super(firstPos, secondPos, height, weight, team);
        this.gameStart = gameStart;
        this.win = win;
        this.lose = lose;
        this.pitchSo = pitchSo;
        this.bb = bb;
        this.hbp = hbp;
        this.save = save;
        this.hold = hold;
        this.era = era;
        this.inningPitched = inningPitched;
        this.pitchType = pitchType;
    }

    public Pitcher(String firstname, String lastname, String username, String mail, String password, String firstPos, String secondPos, int height, int weight, Team team, int gameStart, int win, int lose, int pitchSo, int bb, int hbp, int save, int hold, String era, String inningPitched, List<String> pitchType) {
        super(firstname, lastname, username, mail, password, firstPos, secondPos, height, weight, team);
        this.gameStart = gameStart;
        this.win = win;
        this.lose = lose;
        this.pitchSo = pitchSo;
        this.bb = bb;
        this.hbp = hbp;
        this.save = save;
        this.hold = hold;
        this.era = era;
        this.inningPitched = inningPitched;
        this.pitchType = pitchType;
    }

    public Pitcher(String firstname, String lastname, String profilepic, String username, String mail, String password, String userId, String dob, String avg, String firstPos, String secondPos, int hr, int rbi, int sb, int so, int height, int weight, Team team, int gameStart, int win, int lose, int pitchSo, int bb, int hbp, int save, int hold, String era, String inningPitched, List<String> pitchType) {
        super(firstname, lastname, profilepic, username, mail, password, userId, dob, avg, firstPos, secondPos, hr, rbi, sb, so, height, weight, team);
        this.gameStart = gameStart;
        this.win = win;
        this.lose = lose;
        this.pitchSo = pitchSo;
        this.bb = bb;
        this.hbp = hbp;
        this.save = save;
        this.hold = hold;
        this.era = era;
        this.inningPitched = inningPitched;
        this.pitchType = pitchType;
    }

    public Pitcher(String avg, String firstPos, String secondPos, int hr, int rbi, int sb, int so, int height, int weight, Team team, int gameStart, int win, int lose, int pitchSo, int bb, int hbp, int save, int hold, String era, String inningPitched, List<String> pitchType) {
        super(avg, firstPos, secondPos, hr, rbi, sb, so, height, weight, team);
        this.gameStart = gameStart;
        this.win = win;
        this.lose = lose;
        this.pitchSo = pitchSo;
        this.bb = bb;
        this.hbp = hbp;
        this.save = save;
        this.hold = hold;
        this.era = era;
        this.inningPitched = inningPitched;
        this.pitchType = pitchType;
    }

    public Pitcher(String firstname, String lastname, String username, String mail, String password, String avg, String firstPos, String secondPos, int hr, int rbi, int sb, int so, int height, int weight, Team team, int gameStart, int win, int lose, int pitchSo, int bb, int hbp, int save, int hold, String era, String inningPitched, List<String> pitchType) {
        super(firstname, lastname, username, mail, password, avg, firstPos, secondPos, hr, rbi, sb, so, height, weight, team);
        this.gameStart = gameStart;
        this.win = win;
        this.lose = lose;
        this.pitchSo = pitchSo;
        this.bb = bb;
        this.hbp = hbp;
        this.save = save;
        this.hold = hold;
        this.era = era;
        this.inningPitched = inningPitched;
        this.pitchType = pitchType;
    }

    public int getGameStart() {
        return gameStart;
    }

    public void setGameStart(int gameStart) {
        this.gameStart = gameStart;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getPitchSo() {
        return pitchSo;
    }

    public void setPitchSo(int pitchSo) {
        this.pitchSo = pitchSo;
    }

    public int getBb() {
        return bb;
    }

    public void setBb(int bb) {
        this.bb = bb;
    }

    public int getHbp() {
        return hbp;
    }

    public void setHbp(int hbp) {
        this.hbp = hbp;
    }

    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getInningPitched() {
        return inningPitched;
    }

    public void setInningPitched(String inningPitched) {
        this.inningPitched = inningPitched;
    }

    public List<String> getPitchType() {
        return pitchType;
    }

    public void setPitchType(List<String> pitchType) {
        this.pitchType = pitchType;
    }
}
