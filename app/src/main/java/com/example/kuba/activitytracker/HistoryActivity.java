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


        activityLoading(layout);
    }

    public void activityLoading(LinearLayout layout){

        if (GPS.getGPS().getHistoryActivity().getHistory().size() == 0) {
            Toast toast = Toast.makeText(this,"Nie zarejestrowano jeszcze żadnej aktywności", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            for (Log i : GPS.getGPS().getHistoryActivity().getHistory()) {
                System.out.println("wchodze do elsa");
                final int index = i.getNr();
                Button button = new Button(this);
                button.setText(i.toString());

                System.out.println("dodaje listenera");

                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        GPS.getGPS().getHistoryActivity().setChoosed(index);
                        Intent intent = new Intent(getApplicationContext(),RecordedActivity.class);
                        startActivity(intent);
                    }
                });

                layout.addView(button);
            }

        }
    }
}
