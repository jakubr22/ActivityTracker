package com.example.kuba.activitytracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kuba.activitytracker.core.GPS;
import com.example.kuba.activitytracker.core.IActivity;
import com.example.kuba.activitytracker.core.Point;

import java.util.Date;

public class CaloriesCounterActivity extends AppCompatActivity implements IActivity {
    double przebytyDystans = 0, predkoscAktualna, predkoscSrednia;
    private int spaloneKalorie = 0, czasTrwaniaAktywnosci = 0;
    private boolean licz = false, pause = false;
    private GPS gps;
    private Point lastKnownPosition;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private double calories = 0, currentSpeed = 0, averageSpeed = 0, distance = 0,  sumDistance=0;
    private long sumSpeed = 0;
    private int time = 0;
    private double userWeight = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_counter);
        sharedPreferences = getSharedPreferences("com.example.artur.aehr", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gps = GPS.getGPS();
        gps.addActivity(this);
    }

    double getTimeInterval(Point pStart, Point pNext) { //obliczanie roznicy czasu pomiedzy punktami pomiarowymi GPS
        Date dateStart = pStart.getDate(), dateNext = pNext.getDate();
        double timeInterval = dateNext.getTime() - dateStart.getTime();
        timeInterval /= 1000;
        return timeInterval;
    }

    int getDistanceBetweenPoints(Point pStart, Point pStop) {/** Calculates the distance in m between two lat/long points* using the haversine formula
     z uwzglednieniem roznic wysokosci*/
        double longtitudeStart = pStart.getLongitude(), longtitudeStop = pStop.getLongitude(), latitudeStart = pStart.getLatitude(), latitudeStop = pStop.getLatitude();
        double altitudeStar = pStart.getAltitude(), altitudeStop = pStop.getAltitude();
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(latitudeStop - latitudeStart);
        double dLon = Math.toRadians(longtitudeStop - longtitudeStart);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latitudeStart)) * Math.cos(Math.toRadians(latitudeStop))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = r * c * 1000;
        double height = altitudeStop - altitudeStar;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        distance = Math.sqrt(distance);

        return (int) distance;
    }


    double calculateSpeed(int distance, double timeInterval) { //obliczanie predkosci w km/h
        double speed = (double) (distance / timeInterval) * 3.6;
        speed *= 100;
        speed = Math.round(speed);
        speed /= 100; //zaokraglenie do dwoch miejsc po przecinku
        return speed;
    }

    double getKcalNaKgNaSecBiegu(double speed) { //predkosc w km/h; zwraca nam wartosc spalanych kalorii na kg masy ciala przy podanej predkosci
        double mTKm = 1.609;
        if (speed < 2 * mTKm) return 2.0;
        else if (speed < 2.5 * mTKm) return 3.0;
        else if (speed < 3 * mTKm) return 3.5;
        else if (speed < 3.5 * mTKm) return 4.3;
        else if (speed < 4 * mTKm) return 5.0;
        else if (speed < 4.5 * mTKm) return 7.0;
        else if (speed < 5 * mTKm) return 8.3;
        else if (speed < 6 * mTKm) return 9.8;
        else if (speed < 7 * mTKm) return 11.0;
        else if (speed < 8 * mTKm) return 11.8;
        else if (speed < 9 * mTKm) return 12.8;
        else if (speed < 10 * mTKm) return 14.5;
        else if (speed < 11 * mTKm) return 16.0;
        else if (speed < 12 * mTKm) return 19.0;
        else if (speed < 13 * mTKm) return 20.8;
        else if (speed < 14 * mTKm) return 23.0;
        else return 24.5;
    }

    double getKcalNaKgNaSecJazdyRowerem(double speed) {//predkosc w km/h
        double mTKm = 1.609;

        if (speed < 5.5 * mTKm) return 3.5;
        else if (speed < 9.4 * mTKm) return 5.8;
        else if (speed < 12 * mTKm) return 6.8;
        else if (speed < 14 * mTKm) return 8.0;
        else if (speed < 16 * mTKm) return 10.0;
        else if (speed < 19.5 * mTKm) return 12.0;
        else return 15.8;
    }

    double getKcalNaKgNaSecJazdyNaRolkach(double speed) {//predkosc w km/h
        double mTKm = 1.609;

        if (speed < 7 * mTKm) return 5.0;
        else if (speed < 9 * mTKm) return 7.5;
        else if (speed < 11 * mTKm) return 9.8;
        else if (speed < 13 * mTKm) return 12.3;
        else if (speed < 15 * mTKm) return 14.0;
        else return 15.0;
    }

    public void activityPause(View view) {
        Button button = (Button) findViewById(R.id.pause);
        if (pause) {
            button.setText("włącz");
        } else {
            button.setText("wyłącz");
        }
        gps.setLogHistory(pause);
        pause = !pause;
    }

    public void activityRecording(View view) {
        Button button = (Button) findViewById(R.id.button);
        if (licz == false) {
            button.setText("Stop");
            findViewById(R.id.pause).setEnabled(true);
            licz = true;

            String weight = sharedPreferences.getString("weight", "");

            if (weight != "") {
                userWeight = Double.parseDouble(weight);
            }


            Point prev = gps.getHistory().get(0);
            for (Point i : gps.getHistory()) {
                if (prev != i) {
                    count(prev, i);
                    lastKnownPosition = prev = i;
                }
            }

            show();


        } else {
            button.setText("Licz");
            findViewById(R.id.pause).setEnabled(false);
            ((Button) findViewById(R.id.pause)).setText("włącz");
            pause = false;
            licz = false;
            calories =  currentSpeed =  averageSpeed = distance = sumDistance= sumSpeed=0;
            //gps.cleanHistory();
        }

    }

    /**
     * wykonuje obliczenia do potrzebne do oczacowania kalorii
     */
    private void count(Point prev, Point next) {
        calories += getKcalNaKgNaSecBiegu(next.getSpeed()) * getTimeInterval(prev, next) * userWeight;
        currentSpeed = calculateSpeed(getDistanceBetweenPoints(prev, next), getTimeInterval(prev, next));
        sumSpeed += currentSpeed;
        sumDistance += getDistanceBetweenPoints(prev, next);
        time += getTimeInterval(prev, next);
    }

    /**
     * pokazuje na ekranie wyniki
     */
    private void show() {
        averageSpeed = sumSpeed / gps.getHistory().size();
        distance = sumDistance / 1000;

        TextView kalorie = (TextView) findViewById(R.id.textView11);
        kalorie.setText(String.format("%.0f", calories) + " kcal");

        TextView aktualnaPredkosc = (TextView) findViewById(R.id.textView12);
        aktualnaPredkosc.setText(String.format("%.2f", currentSpeed) + " km/h");

        TextView sredniaPredkosc = (TextView) findViewById(R.id.textView13);
        sredniaPredkosc.setText(String.format("%.2f", averageSpeed) + " km/h");

        TextView przebytyDystans = (TextView) findViewById(R.id.textView14);
        przebytyDystans.setText(String.format("%.1f", distance) + " km");

        TextView czasTrwania = (TextView) findViewById(R.id.textView15);
        String czas = secondsToTimeFormat(time);
        czasTrwania.setText(czas);
    }

    String secondsToTimeFormat(int timeInterval) { //Funkcja zmieniajaca interwal czasowy wyrazony w sekundach
        String time = "";                   //na format hh:mm:ss aktywnosci
        int hours, minutes, seconds, pomTimeInterval = timeInterval;
        if (timeInterval > 0) {
            if (timeInterval % 3600 != 0) {
                pomTimeInterval -= (timeInterval % 3600);
                hours = pomTimeInterval / 3600;
                if (hours < 10) {
                    time = "0" + hours + "h";
                } else {
                    time = hours + "h";
                }
                timeInterval = timeInterval % 3600; //pozostale minuty + sekundy

                if (timeInterval % 60 != 0) {
                    pomTimeInterval = timeInterval;
                    pomTimeInterval -= (timeInterval % 60);
                    minutes = pomTimeInterval / 60;
                    if (minutes < 10) {
                        time = time + "0" + minutes + "m";
                    } else {
                        time = time + minutes + "m";
                    }
                    timeInterval = timeInterval % 60; //pozostale sekundy

                    if (timeInterval < 10) {
                        time = time + "0" + timeInterval + "s";
                    } else {
                        time = time + timeInterval + "s";
                    }
                    return time;
                } else {
                    minutes = timeInterval / 60;
                    time = time + minutes + "m00s";
                    return time;
                }
            } else {
                hours = timeInterval / 3600;
                time = hours + "hh00m00s";
                return time;
            }
        } else {
            time = "00h00m00s";
            return time;
        }
    }

    @Override
    public void refresh() {
        if (!licz) return;
        Point curPos = new Point(gps.getLoc(), true);

        count(lastKnownPosition, curPos);
        show();

        lastKnownPosition = curPos;
    }

    public void onStop() {
        super.onStop();
        gps.delActivity(this);
    }
}
