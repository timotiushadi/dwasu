package com.mobprog.ius.dwasu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import java.util.Calendar;

public class addNewReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button startTime;
    Button endTime;
    Button meditConfirm_button;
    int startHour;
    int startMinute;
    int endHour;
    int endMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_reminder);


        //  initiate the edit text
        startTime = findViewById(R.id.startTimePick);
        startTime.setText(String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE)));
        // perform click event listener on edit text
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                startHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                startMinute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mStartTimePicker;
                mStartTimePicker = new TimePickerDialog(addNewReminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedStartHour, int selectedStartMinute) {
                        if(selectedStartMinute == 0 ) {
                            startTime.setText(selectedStartHour + ":00");
                        }
                        else if(selectedStartMinute < 10){
                            if (selectedStartHour < 10){
                                startTime.setText("0" + selectedStartHour + ":0" + selectedStartMinute);
                            }
                        }
                        else {
                            startTime.setText(selectedStartHour + ":" + selectedStartMinute);
                        }
                    }
                }, startHour, startMinute, true);//Yes 24 hour time
                mStartTimePicker.show();
            }
        });

        endTime = findViewById(R.id.endTimePick);
        endTime.setText(String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE)));
        // perform click event listener on edit text
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                endHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                endMinute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mEndTimePicker;
                mEndTimePicker = new TimePickerDialog(addNewReminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedEndHour, int selectedEndMinute) {
                        if(selectedEndMinute == 0 ) {
                            if (selectedEndHour < 10){
                                endTime.setText("0" + selectedEndHour + ":00");
                            }
                        }
                        else if(selectedEndMinute < 10){
                            if (selectedEndHour < 10){
                                endTime.setText("0" + selectedEndHour + ":0" + selectedEndMinute);
                            }
                        }
                        else {
                            endTime.setText(selectedEndHour + ":" + selectedEndMinute);
                        }
                    }
                }, endHour, endMinute, true);//Yes 24 hour time
                mEndTimePicker.show();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.timeIntervalPick);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.listtimer, R.layout.spinner_support);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        meditConfirm_button = findViewById(R.id.editConfirm_button);
        meditConfirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}