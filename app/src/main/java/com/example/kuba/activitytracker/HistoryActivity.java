package com.example.kuba.activitytracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.LinkedHashSet;
import java.util.Set;

public class HistoryActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        sharedPreferences = getSharedPreferences("com.example.artur.aehr", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        LinearLayout layout = (LinearLayout)findViewById(R.id.dynamicLayout);
        Button myButton1 = new Button(this);
        myButton1.setText("Przykład");
        Button myButton2 = new Button(this);
        myButton2.setText("Przykład 2");

        activityLoading(layout);


        layout.addView(myButton1);
        layout.addView(myButton2);
    }

    public void activityLoading(LinearLayout layout){
        int activityNumber = sharedPreferences.getInt("activityNumber", 0);
        Set parametry;
        System.out.println("rozpoczynam ladowanie");
        if(activityNumber == 0){
            Toast toast = Toast.makeText(this,"Nie zarejestrowano jeszcze żadnej aktywności", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            for(int i=0; i<activityNumber; i++){
                parametry = sharedPreferences.getStringSet("activity"+(i+1),null);
                if(parametry != null){
                    System.out.println("not a null");
                    String[] parametryArray = (String[]) parametry.toArray();
                    String message = parametryArray[0]+" "+parametryArray[1]+" "+parametryArray[4];
                    Button button = new Button(this);
                    button.setText(message);
                    layout.addView(button);
                }
            }
        }
    }
}
