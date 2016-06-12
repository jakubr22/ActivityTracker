package com.example.kuba.activitytracker.core;


import android.location.Location;

import java.util.Date;
/**
 * przechowuje lokacje oraz czas
 *
 */
public class Point {
    //
    private double longitude;
    private double latitude;
    private double altitude;
    private float speed;
    private Date date;
    private boolean logHistory;

    public Point(Location loc, boolean log) {
        longitude = loc.getLongitude(); //długość
        latitude = loc.getLatitude();   //szerokość
        altitude = loc.getAltitude();   //wysokość
        this.date = new Date();
        speed = loc.getSpeed();
        logHistory=log;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Date getDate() {
        return date;
    }

    public double getAltitude() {
        return altitude;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isLogHistory() {
        return logHistory;
    }
}
