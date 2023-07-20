package com.example.baseballmanagementapp.models;

import java.util.ArrayList;
import java.util.List;

public class Practice extends Event{
    List<String> listOfExercise = new ArrayList<String>();

    public Practice(String teamId, String name, String eventId, String location, String timeStart, String timeEnd, List<String> listOfExercise) {
        super(teamId, name, eventId, location, timeStart, timeEnd);
        this.listOfExercise = listOfExercise;
    }

    public Practice(String teamId, String name, String eventId, String location, String timeStart, String timeEnd) {
        super(teamId, name, eventId, location, timeStart, timeEnd);
    }

    public List<String> getListOfExercise() {
        return listOfExercise;
    }

    public void setListOfExercise(List<String> listOfExercise) {
        this.listOfExercise = listOfExercise;
    }
}
