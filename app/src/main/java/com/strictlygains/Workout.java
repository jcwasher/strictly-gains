package com.strictlygains;

import java.util.ArrayList;

public class Workout {
    private String name;
    private int id;
    private ArrayList<Exercise> exerciseList;

    public Workout() { }

    public Workout(int i) {
        id = i;
        exerciseList = new ArrayList<>();
    }

    public Workout(int i, String n) {
        this(i);
        name = n;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setID(int i) {
        id = i;
    }

    public int getID() {
        return id;
    }

    public Exercise getExercise(int index) {
        return exerciseList.get(index);
    }

    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<Exercise> eList) {
        exerciseList.addAll(eList);
    }

    public void addExercise(Exercise e) {
        exerciseList.add(e);
    }

    public void removeExercise(int index) {
        exerciseList.remove(index);
    }
}
