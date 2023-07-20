package com.example.baseballmanagementapp.models;

public class Event {
    String name, eventId, location;
    String timeStart, timeEnd;

    String teamId;


    public Event(){

    }

    public Event(String teamId, String name, String eventId, String location, String timeStart, String timeEnd) {
        this.teamId = teamId;
        this.name = name;
        this.eventId = eventId;
        this.location = location;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
