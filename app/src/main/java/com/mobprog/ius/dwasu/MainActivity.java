package com.mobprog.ius.dwasu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private LinkedList<String> mAlarmList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private alarmListAdapter mAdapter;

    int limit = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSharedPreferences("Dwasu", 0) == null || !getSharedPreferences("Dwasu", 0).contains("user")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        for (int a = 1; a<limit; a++){
            mAlarmList.addLast("Alarm "+1);
        }

        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerView);
        // Create an adapter and supply the data to be displayed.
//        mAdapter = new alarmListAdapter(this, mAlarmList);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button mnewReminderButton = findViewById(R.id.newReminder);
        mnewReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getSimpleName(),"Button Clicked");
                startActivity(new Intent(v.getContext(), addNewReminder.class));
            }
        });

        ImageButton mlogOutButton = findViewById(R.id.logOutButton);
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