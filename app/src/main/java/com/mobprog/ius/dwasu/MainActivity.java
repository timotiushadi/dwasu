package com.mobprog.ius.dwasu;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<MyListDataTimer> myListDataTimers = new ArrayList<>();

    public boolean isFragmentAddNewAlarmDisplayed = false;

    static final String STATE_FRAGMENT = "state_of_fragment";

    alarmListAdapter mAdapter;
    RecyclerView mRecyclerView;
    Notification notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            notification.createNotificationChannel("Primary", "Timer Notification", "Notif when Timer is done");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mRecyclerView = findViewById(R.id.recyclerView);

        ((TextView) findViewById(R.id.textUserName)).setText("Hello " + getSharedPreferences("Dwasu", 0).getString("user", ""));

        if (getSharedPreferences("Dwasu", 0) == null || !getSharedPreferences("Dwasu", 0).contains("user")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        if (savedInstanceState != null)
            isFragmentAddNewAlarmDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);

        if (isInternetAvailable()){
            try {
                getJSON("https://ius.mobile.indoserver.web.id/getData.php");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swipeRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myListDataTimers.clear();
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }

            private void refreshData() {
                if (isInternetAvailable()){
                    try {
                        getJSON("https://ius.mobile.indoserver.web.id/getData.php");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Button mnewReminderButton = findViewById(R.id.newReminder);
        mnewReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getSimpleName(),"Button Clicked");
                displayFragment();
            }
        });

        ImageButton mlogOutButton = findViewById(R.id.logOutButton);
        mlogOutButton.setOnClickListener(new View.OnClickListener() {
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

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject g = jsonArray.getJSONObject(i);

            String pos = g.getString("pos");
            String startHour = g.getString("startHour");
            String endHour = g.getString("endHour");
            String intervalWaktu = g.getString("intervalWaktu");

            MyListDataTimer isianTimer = new MyListDataTimer(startHour, endHour, intervalWaktu, pos);

            myListDataTimers.add(isianTimer);
        }

        // Create an adapter and supply the data to be displayed.
        mAdapter = new alarmListAdapter(myListDataTimers);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean isInternetAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}