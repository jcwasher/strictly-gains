package com.strictlygains.data;

import java.util.ArrayList;

public class Workout {
    private int id;
    private ArrayList<Exercise> exerciseList;

    public Workout(int i) {
        id = i;
    }

    public void setID(int i) {
        id = i;
    }

    public int getID() {
        return id;
    }

    public Exercise getExercise(int exerciseNum) {
        return exerciseList.get(exerciseNum - 1);
    }

    public void addExercise(Exercise e) {
        exerciseList.add(e);
    }

    public void removeExercise(int exerciseNum) {
        exerciseList.remove(exerciseNum - 1);
    }
}
