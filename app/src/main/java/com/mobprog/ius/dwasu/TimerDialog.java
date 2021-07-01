package com.mobprog.ius.dwasu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TimerDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_dialog);

        Button mbtnResetAlarm = findViewById(R.id.btnPostponeAlarm);
        mbtnResetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getSimpleName(),"Button Clicked");
                reset();
            }
        });

        Button mbtnStopAlarm = findViewById(R.id.btnStopAlarm);
        mbtnStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getSimpleName(),"Button Clicked");
                stop();
                displayFragment();
            }
        });

    }

    public void postpone(){

    }

    public void stop(){

    }

    public void reset(){

    }

    public void displayFragment(){
        AddGlassFragment addGlassFragment = AddGlassFragment.newInstance();
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.FragmentContainer_AddGlassDialog,
                addGlassFragment).addToBackStack(null).commit();
    }
}