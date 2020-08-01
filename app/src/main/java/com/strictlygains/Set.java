package com.strictlygains;

import java.util.Date;

public class Set {
    private double weight;
    private int reps;
    private boolean success;
    private boolean warmup;
    private int rpe;
    private long date;

    public Set() { }

    public Set(double w, int rep) {
        this(w, rep, false);
    }

    public Set(double w, int rep, int rp) {
        this(w, rep, false);
        rpe = rp;
    }

    public Set(double w, int rep, boolean suc) {
        weight = w;
        reps = rep;
        success = suc;
        warmup = false;
        rpe = 0;
        date = new Date().getTime();
    }

    public Set(double w, int rep, boolean suc, int rp) {
        this(w, rep, suc);
        warmup = false;
        rpe = rp;
    }

    public void setWeight(double w) {
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

    public long getDate() {
        return date;
    }

    public void setDate(long d) {
        date = new Date().getTime();
    }
}
