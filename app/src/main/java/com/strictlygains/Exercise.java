package com.strictlygains;

import java.util.ArrayList;

public class Exercise {
    private int id;
    private int max;
    private String name;
    private String focus;
    private ArrayList<Set> setList;

    public Exercise() { }

    public Exercise(int i, int m, String n, String f) {
        id = i;
        max = m;
        name = n;
        focus = f;
        setList = new ArrayList<>();
    }

    public void setID(int i) {
        id = i;
    }

    public void setMax(int m) { max = m; }

    public void setName(String n) {
        name = n;
    }

    public void setFocus(String f) {
        focus = f;
    }

    public int getID() {
        return id;
    }

    public int getMax() {
        return max;
    }

    public String getName() {
        return name;
    }

    public String getFocus() {
        return focus;
    }

    public Set getSet(int index) {
        return setList.get(index);
    }

    public void setSetList(ArrayList<Set> sList) {
        setList.addAll(sList);
    }

    public ArrayList<Set> getSetList() {
        return setList;
    }

    public void addSet(Set s) {
        setList.add(s);
    }

    public void removeSet(int index) {
        setList.remove(index);
    }

    public void startSet() {
        // interact with UI? may be unnecessary
    }

    public void endSet() {
        // interact with UI? may be unnecessary
    }
}
