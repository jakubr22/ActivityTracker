package com.example.kuba.activitytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.kuba.activitytracker.core.GPS;
import com.example.kuba.activitytracker.core.IActivity;

public class GPS_Activity extends AppCompatActivity implements IActivity{
    TextView provider;
    TextView longitude;
    TextView latitude;
    TextView history;
    GPS gps;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        provider = (TextView) findViewById(R.id.textViewProvider);
        longitude = (TextView) findViewById(R.id.textViewHeight);
        latitude = (TextView) findViewById(R.id.textViewWidth);
        history = (TextView) findViewById(R.id.textViewHistory);

        gps = GPS.getGPS();
        //gps.setGPS_actv(this);
        gps.addActivity(this);





        provider.setText("Dostawca: " + gps.getBestProvider());
        longitude.setText("Dlugość: " + gps.getLongitude());
        latitude.setText("Szerokość: " + gps.getLatitude());

    }




    public void refresh() {
        history.setText(history.getText() + "\n" + gps.getLongitude() + "\\" + gps.getLatitude());
        provider.setText("Dostawca: " + gps.getBestProvider());
        longitude.setText("Dlugość: " + gps.getLongitude());
        latitude.setText("Szerokość: " + gps.getLatitude());
    }
    public void onStop(){
        super.onStop();
        gps.delActivity(this);
    }

}
