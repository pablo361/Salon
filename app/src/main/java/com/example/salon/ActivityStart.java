package com.example.salon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.salon.logIn.activityLogIn;

public class ActivityStart extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    public String SHARED_PREF_NAME ="user_pref";
    public SharedPreferences.Editor sharedPrefEditor;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_star);
        init();
        boolean estLogeado = sharedPreferences.getBoolean("logeado", false);
        Intent i;
        if (estLogeado){
            i = new Intent(this,activityHome.class);
        } else {
            i = new Intent(this, activityLogIn.class);
        }
        startActivity(i);
    }


    protected void init() {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

    }
}