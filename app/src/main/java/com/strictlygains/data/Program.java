package com.strictlygains.data;

import java.util.ArrayList;

public class Program {
    private int id;
    private String name;
    private String difficulty;
    private ArrayList<Workout> workoutList;

    public Program(int i, String n, String d) {
        id = i;
        name = n;
        difficulty = d;
    }

    public void setID(int i) {
        id = i;
    }

    public void setName(String n) {
        name = n;
    }

    public void setDifficulty(String d) {
        difficulty = d;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void addWorkout(Workout w) {
        workoutList.add(w);
    }

    public void removeWorkout(int workoutNum) {
        workoutList.remove(workoutNum - 1);
    }
}
