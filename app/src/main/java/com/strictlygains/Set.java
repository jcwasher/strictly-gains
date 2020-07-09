package com.strictlygains;

public class Set {
    private double weight;
    private int reps;
    private boolean success;
    private boolean warmup;
    private int rpe;

    public Set() { }

    public Set(int w, int rep, int rp) {
        weight = w;
        reps = rep;
        success = false;
        warmup = false;
        rpe = rp;
    }

    public void setWeight(int w) {
        weight = w;
    }

    public void setReps(int r) {
        reps = r;
    }

    public void setSuccess(boolean s) {
        success = s;
    }

    public void setWarmup(boolean w) {
        warmup = w;
    }

    public void setRPE(int r) {
        rpe = r;
    }

    public double getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isWarmup() {
        return warmup;
    }

    public int getRPE() {
        return rpe;
    }
}