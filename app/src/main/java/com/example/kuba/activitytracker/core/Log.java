package com.example.kuba.activitytracker.core;

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

    public Log(double calories, double averageSpeed, double distance, double time) {
        log = GPS.getGPS().getHistory();
        this.calories = calories;
        this.averageSpeed = averageSpeed;
        this.distance = distance;
        this.time = time;
    }

    public String toString() {

        return log.getFirst().getDate().toString();

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
}
