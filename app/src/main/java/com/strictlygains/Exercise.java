package com.strictlygains;

import java.util.ArrayList;

public class Exercise {
    private int id;
    private double max, goal;
    private String name;
    private String focus;
    private ArrayList<Set> setList;

    public Exercise() {
        setList = new ArrayList<Set>();
    }

    public Exercise(int i, double m, double g, String n, String f) {
        this();
        id = i;
        max = m;
        goal = g;
        name = n;
        focus = f;
    }

    public void setID(int i) {
        id = i;
    }

    public void setMax(double m) { max = m; }

    public void setGoal(double g) { goal = g; }

    public void setName(String n) {
        name = n;
    }

    public void setFocus(String f) {
        focus = f;
    }

    public int getID() {
        return id;
    }

    public double getMax() {
        return max;
    }

    public double getGoal() {
        return goal;
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

}
