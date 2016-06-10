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
    Date date;
    static private int nr=0;



    public Point(Location loc) {
        System.out.println("dodano punkt "+nr);
        nr++;
        longitude = loc.getLongitude(); //długość
        latitude = loc.getLatitude();   //szerokość
        altitude = loc.getAltitude();   //wysokość
        this.date = new Date();
        speed = loc.getSpeed();

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

}
