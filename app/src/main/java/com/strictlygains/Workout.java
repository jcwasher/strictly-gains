package com.strictlygains;

import java.util.ArrayList;

public class Workout {
    private int id;
    private ArrayList<Exercise> exerciseList;

    public Workout() { }

    public Workout(int i) {
        id = i;
        exerciseList = new ArrayList<>();
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

    public void setExerciseList(ArrayList<Exercise> eList) {
        exerciseList.addAll(eList);
    }

    public void addExercise(Exercise e) {
        exerciseList.add(e);
    }

    public void removeExercise(int exerciseNum) {
        exerciseList.remove(exerciseNum - 1);
    }
}
