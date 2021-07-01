package com.mobprog.ius.dwasu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinkedList<String> mintervalTimeList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private alarmListAdapter mAdapter;

    public boolean isFragmentAddNewAlarmDisplayed = false;

    static final String STATE_FRAGMENT = "state_of_fragment";

    Random r = new Random();
    int rn = r.nextInt(120 - 15) + 15;
    int limit = 5;
    long currentHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSharedPreferences("Dwasu", 0) == null || !getSharedPreferences("Dwasu", 0).contains("user")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerView);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new alarmListAdapter(this, mintervalTimeList);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (int a = 1; a<=limit; a++){
            mintervalTimeList.addLast("Interval "+ rn + " menit");
        }

        if (savedInstanceState != null)
            isFragmentAddNewAlarmDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);

        Button mnewReminderButton = findViewById(R.id.newReminder);
        mnewReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getSimpleName(),"Button Clicked");
                displayFragment();
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

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentAddNewAlarmDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void closeFragment(){
        // Get the FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing.
        AddNewAlarmFragment simpleFragment = (AddNewAlarmFragment) fragmentManager
                .findFragmentById(R.id.FragmentContainer_AddNewAlarm);
        if (simpleFragment != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        // Set boolean flag to indicate fragment is closed.
        isFragmentAddNewAlarmDisplayed = false;
    }

    public void displayFragment(){
        AddNewAlarmFragment simpleFragment = AddNewAlarmFragment.newInstance();
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.FragmentContainer_AddNewAlarm,
                simpleFragment).addToBackStack(null).commit();

        // Set boolean flag to indicate fragment is open.
        isFragmentAddNewAlarmDisplayed = true;
    }

}