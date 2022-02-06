package com.application.weatherappversion1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=2000;
String password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(password.equals("")) {
                    Intent i = new Intent(SplashActivity.this,
                            LoginActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                }
                else{
                    Intent i = new Intent(SplashActivity.this,
                            MainActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                }//invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
    void getData() {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
      password=  sharedPref.getString("password", "");
    }
}