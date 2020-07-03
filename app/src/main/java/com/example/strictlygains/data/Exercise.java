package com.example.strictlygains.data;

import java.util.ArrayList;

public class Exercise {
    private int id;
    private String name;
    private String focus;
    private ArrayList<Set> setList;

    public Exercise(int i, String n, String f) {
        id = i;
        name = n;
        focus = f;
    }

    public void setID(int i) {
        id = i;
    }

    public void setName(String n) {
        name = n;
    }

    public void setFocus(String f) {
        focus = f;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFocus() {
        return focus;
    }

    public Set getSet(int setNum) {
        return setList.get(setNum - 1);
    }

    public void addSet(Set s) {
        setList.add(s);
    }

    public void removeSet(int setNum) {
        setList.remove(setNum - 1);
    }

    public void startSet() {
        // interact with UI? may be unnecessary
    }

    public void endSet() {
        // interact with UI? may be unnecessary
    }
}
