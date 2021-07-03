package com.mobprog.ius.dwasu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<MyListDataTimer> myListDataTimers = new ArrayList<>();

    public boolean isFragmentAddNewAlarmDisplayed = false;

    static final String STATE_FRAGMENT = "state_of_fragment";

    alarmListAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.textUserName)).setText("Hello " + getSharedPreferences("Dwasu", 0).getString("user", ""));

        if (getSharedPreferences("Dwasu", 0) == null || !getSharedPreferences("Dwasu", 0).contains("user")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new alarmListAdapter(myListDataTimers);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new GetTimer().execute();

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
        AddNewTimerFragment simpleFragment = (AddNewTimerFragment) fragmentManager
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
        AddNewTimerFragment simpleFragment = AddNewTimerFragment.newInstance();
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

    private class GetTimer extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Checking data timer", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            myListDataTimers = new ArrayList<>();
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://ius.mobile.indoserver.web.id/getData.php";
            String jsonStr = sh.makeServiceCall(url);

            Log.e("Donlot json", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject dataTimer = jsonObj.getJSONObject("Data Timer");
                    JSONArray datasTimer = dataTimer.getJSONArray("dataTimer");

                    // looping through All Contacts
                    for (int i = 0; i < datasTimer.length(); i++) {
                        JSONObject g = datasTimer.getJSONObject(i);

                        String pos = g.getString("pos");
                        String startHour = g.getString("startHour");
                        String endHour = g.getString("endHour");
                        String intervalWaktu = g.getString("intervalWaktu");

                        MyListDataTimer isianTimer = new MyListDataTimer(pos, startHour, endHour, intervalWaktu);

                        myListDataTimers.add(isianTimer);

                        Log.e("Donlot json", "Json total : " + myListDataTimers.size());
                    }
                } catch (final JSONException e) {
                    Log.e("Donlot json", "Json parsing error: " + e.getMessage());

                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mAdapter = new alarmListAdapter(myListDataTimers);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}