package com.example.kuba.activitytracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.kuba.activitytracker.core.GPS;

public class MainMenuActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    GPS gps;
    TextView textView;


    void setName(){
        String massage = "Witaj "+ sharedPreferences.getString("name", "Nowy użytkowniku") + "!";
        textView.setText(massage);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        sharedPreferences = getSharedPreferences("com.example.artur.aehr", Context.MODE_PRIVATE);
        textView = (TextView)findViewById(R.id.textView16);
        setName();


            try {    // w przypadku braku fixa wyjatek jest łapany

                gps = new GPS((LocationManager) getSystemService(LOCATION_SERVICE));
                gps.getLongitude();
            } catch (Exception e) {
                //((Button) findViewById(R.id.button1)).setEnabled(false);
                //((Button) findViewById(R.id.button2)).setEnabled(false);
                //((Button) findViewById(R.id.button3)).setEnabled(false);
                //con=true;
                   // Thread.sleep(1000);
            }
    }

    @Override
    protected void onResume() {

       super.onResume();
        setName();
    }

    public void openUserInfoActivity(View view) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }

    public void openCaloriesCounterActivity(View view) {
        Intent intent = new Intent(this, CaloriesCounterActivity.class);
        startActivity(intent);
    }

    public void openHistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
