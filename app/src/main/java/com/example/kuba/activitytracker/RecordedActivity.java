package com.example.kuba.activitytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kuba.activitytracker.core.GPS;

public class RecordedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded);
        int choosed = GPS.getGPS().getHistoryActivity().getChoosed();
        double pom;
        String message;

        TextView bieganie = (TextView)findViewById(R.id.textView23);
        message = GPS.getGPS().getHistoryActivity().getHistory().get(choosed).getAktynowść();
        bieganie.setText(message);

        TextView data = (TextView)findViewById(R.id.textView24);
        message = GPS.getGPS().getHistoryActivity().getHistory().get(choosed).toString();

        TextView czasTrwania = (TextView)findViewById(R.id.textView25);
        pom = GPS.getGPS().getHistoryActivity().getHistory().get(choosed).getTime();
        message = secondsToTimeFormat(pom);
        czasTrwania.setText(message);

        TextView kalorie = (TextView)findViewById(R.id.textView26);
        pom = GPS.getGPS().getHistoryActivity().getHistory().get(choosed).getCalories();
        kalorie.setText(String.format("%.0f", pom) + " kcal");


        TextView dystans = (TextView)findViewById(R.id.textView27);
        pom = GPS.getGPS().getHistoryActivity().getHistory().get(choosed).getDistance();
        dystans.setText(String.format("%.2f", pom) + " km");


        TextView sredniaPredkosc = (TextView)findViewById(R.id.textView28);
        pom = GPS.getGPS().getHistoryActivity().getHistory().get(choosed).getAverageSpeed();
        sredniaPredkosc.setText(String.format("%.2f", pom) + " km/h");
    }

    String secondsToTimeFormat(double timeInterval) { //Funkcja zmieniajaca interwal czasowy wyrazony w sekundach
        String time = "";                   //na format hh:mm:ss aktywnosci
        double hours, minutes, seconds, pomTimeInterval = timeInterval;
        if (timeInterval > 0) {
            if (timeInterval % 3600 != 0) {
                pomTimeInterval -= (timeInterval % 3600);
                hours = pomTimeInterval / 3600;
                if (hours < 10) {
                    time = "0" + String.format("%.0f",hours) + "h";
                } else {
                    time = String.format("%.0f",hours) + "h";
                }
                timeInterval = timeInterval % 3600; //pozostale minuty + sekundy

                if (timeInterval % 60 != 0) {
                    pomTimeInterval = timeInterval;
                    pomTimeInterval -= (timeInterval % 60);
                    minutes = pomTimeInterval / 60;
                    if (minutes < 10) {
                        time = time + "0" + String.format("%.0f",hours) + "m";
                    } else {
                        time = time + String.format("%.0f",hours) + "m";
                    }
                    timeInterval = timeInterval % 60; //pozostale sekundy

                    if (timeInterval < 10) {
                        time = time + "0" + String.format("%.0f",hours) + "s";
                    } else {
                        time = time + String.format("%.0f",hours) + "s";
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
}
