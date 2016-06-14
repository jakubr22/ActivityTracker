package com.example.kuba.activitytracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kuba.activitytracker.core.GPS;
import com.example.kuba.activitytracker.core.Log;

public class HistoryActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        sharedPreferences = getSharedPreferences("com.example.artur.aehr", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //editor.clear();
        //editor.commit();
        LinearLayout layout = (LinearLayout)findViewById(R.id.dynamicLayout);
        Button myButton1 = new Button(this);
        myButton1.setText("Przykład");
        myButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RecordedActivity.class);
                startActivity(intent);
            }
        });

        Button myButton2 = new Button(this);
        myButton2.setText("Przykład 2");

        layout.addView(myButton1);
        layout.addView(myButton2);
        activityLoading(layout);
    }

    public void activityLoading(LinearLayout layout){

        if (GPS.getGPS().getHistoryActivity().getHistory().size() == 0) {
            Toast toast = Toast.makeText(this,"Nie zarejestrowano jeszcze żadnej aktywności", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            for (Log i : GPS.getGPS().getHistoryActivity().getHistory()) {
                Button button = new Button(this);
                button.setText(i.toString());
                layout.addView(button);
            }

        }
    }
}
