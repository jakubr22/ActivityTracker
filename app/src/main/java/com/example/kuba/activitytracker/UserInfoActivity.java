package com.example.kuba.activitytracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class UserInfoActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        EditText name = (EditText)findViewById(R.id.editText);
        sharedPreferences = getSharedPreferences("com.example.artur.aehr", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        name.setText(sharedPreferences.getString("name", "userName"));
    }

    public void zapiszDane(View view) {
        EditText name = (EditText)findViewById(R.id.editText);
        String userName = name.getText().toString();
        EditText age = (EditText)findViewById(R.id.editText2);
        String userAge = age.getText().toString();
        EditText weight = (EditText)findViewById(R.id.editText3);
        String userWeight = weight.getText().toString();
        String userSex;
        RadioButton plecZenska = (RadioButton)findViewById(R.id.radioButton);
        if(plecZenska.isChecked()) userSex = "woman";
        else userSex = "man";


        editor.putString("name", userName);
        editor.commit();
        editor.putString("age", userAge);
        editor.commit();
        editor.putString("weight", userWeight);
        editor.commit();
        editor.putString("sex", userSex);
        editor.commit();
    }
}
