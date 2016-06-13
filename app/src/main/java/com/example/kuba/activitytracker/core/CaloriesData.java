package com.example.kuba.activitytracker.core;

/**
 * Created by Artur on 2016-06-13.
 */
public class CaloriesData {

    private boolean licz = false;
    private boolean pause = false;
    private Point lastKnownPosition = null;
    private long points = 0;
    private double calories = 0;
    private double currentSpeed = 0;
    private double averageSpeed = 0;
    private double distance = 0;
    private double sumDistance = 0;
    private double sumSpeed = 0;
    private double time = 0;
    private double userWeight = 0;


    private static CaloriesData instance = null;

    protected CaloriesData() {
        // Exists only to defeat instantiation.
    }

    public static CaloriesData getInstance() {
        if (instance == null) {
            instance = new CaloriesData();
        }
        return instance;
    }

    public void add(double calories, double currentSpeed, double sumDistance, double time) {
        this.calories+=calories;
        this.currentSpeed = currentSpeed;
        this.sumSpeed += this.currentSpeed;
        this.sumDistance+=sumDistance;
        this.time+=time;

    }

    public void clearData() {
        calories = currentSpeed = averageSpeed = distance = sumDistance = sumSpeed = 0;
        points = 0;
        time = 0;
    }

    public boolean isLicz() {
        return licz;
    }

    public void setLicz(boolean licz) {
        this.licz = licz;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public Point getLastKnownPosition() {
        return lastKnownPosition;
    }

    public void setLastKnownPosition(Point lastKnownPosition) {
        this.lastKnownPosition = lastKnownPosition;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSumDistance() {
        return sumDistance;
    }

    public void setSumDistance(double sumDistance) {
        this.sumDistance = sumDistance;
    }

    public double getSumSpeed() {
        return sumSpeed;
    }

    public void setSumSpeed(double sumSpeed) {
        this.sumSpeed = sumSpeed;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(double userWeight) {
        this.userWeight = userWeight;
    }
}
