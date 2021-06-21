package com.mobprog.ius.dwasu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSharedPreferences("Dwasu", 0) == null || !getSharedPreferences("Dwasu", 0).contains("user")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        Button mnewReminderButton = findViewById(R.id.newReminder);
        mnewReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getSimpleName(),"Button Clicked");
                startActivity(new Intent(v.getContext(), addNewReminder.class));
            }
        });

        Button mlogOutButton = findViewById(R.id.logOutButton);
        findViewById(R.id.logOutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("Dwasu", 0).edit().clear().apply();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        });

//        // Get the Drawable custom_progressbar
//        Drawable draw = ResourcesCompat.getDrawable(getResources(), R.drawable.progress_bar, null);
//        // set the drawable as progress drawable
//        ProgressBar.setProgressDrawable(draw);
    }
}