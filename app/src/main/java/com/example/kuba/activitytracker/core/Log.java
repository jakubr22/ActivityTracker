package com.example.kuba.activitytracker.core;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * przechowuje historie pojedyńczej aktywnosci
 */
public class Log {
    private LinkedList<Point> log = new LinkedList<>();
    private double calories = 0;
    private double averageSpeed = 0;
    private double distance = 0;
    private double time = 0;
    private String aktynowść;
    private int nr;

    public Log(double calories, double averageSpeed, double distance, double time, String aktynowść) {
        log = GPS.getGPS().getHistory();
        this.aktynowść = aktynowść;
        this.calories = calories;
        this.averageSpeed = averageSpeed;
        this.distance = distance;
        this.time = time;
    }

    public String toString() {


        Format formatter = new SimpleDateFormat("dd.MM.yy");
        return formatter.format(log.getFirst().getDate());


    }


    public LinkedList<Point> getLog() {
        return log;
    }

    public double getCalories() {
        return calories;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public double getDistance() {
        return distance;
    }

    public double getTime() {
        return time;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }
}
