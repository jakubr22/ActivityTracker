package com.example.kuba.activitytracker;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kuba.activitytracker.core.GPS;

//unused class
public class MainActivity extends AppCompatActivity {
    Button GPSButton;
    Button mapButton;
    GPS gps;
    TextView v1;

    public void onClickGPS(View v) {
        Intent myintent = new Intent(MainActivity.this, GPS_Activity.class);
        MainActivity.this.startActivity(myintent);
    }

    public void onClickMap(View v) {
        Intent myintent = new Intent(MainActivity.this, com.example.kuba.activitytracker.MapActivity.class);
        MainActivity.this.startActivity(myintent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v1 = (TextView) findViewById(R.id.textView1);
        GPSButton = (Button) findViewById(R.id.button1);
        mapButton = (Button) findViewById(R.id.button2);
        try {	// w przypadku braku fixa wyjatek jest łapany
            gps = new GPS((LocationManager) getSystemService(LOCATION_SERVICE));
            gps.getLongitude();
        } catch (Exception e) {
            v1.setText("Problem z ustaleniem lokalizacji");
            GPSButton.setEnabled(false);
            mapButton.setEnabled(false);
        }
    }




}
