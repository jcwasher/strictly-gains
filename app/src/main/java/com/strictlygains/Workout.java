package com.strictlygains;

import java.util.ArrayList;

public class Workout {
    private int id;
    private String name;
    private ArrayList<Exercise> exerciseList;

    public Workout() {
        exerciseList = new ArrayList<>();
    }

    public Workout(String n) {
        this();
        name = n;
    }

    public Workout(int i) {
        this();
        id = i;
    }

    public void setID(int i) {
        id = i;
    }

    public void setName(String n) {
        name = n;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
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
