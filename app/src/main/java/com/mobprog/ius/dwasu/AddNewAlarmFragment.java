package com.mobprog.ius.dwasu;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.entity.mime.content.StringBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;


public class AddNewAlarmFragment extends Fragment {

    MainActivity mainActivity = new MainActivity();
    Button startTime;
    Button endTime;
    Button meditConfirm_button;
    int startHour;
    int startMinute;
    int endHour;
    int endMinute;
    String value;
    long totalSize = 0;
    ProgressDialog progDailog;
    View rootview;

    public AddNewAlarmFragment() {
        // Required empty public constructor
    }

    public static AddNewAlarmFragment newInstance() {

        return new AddNewAlarmFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_new_alarm,
                container, false);

        rootview = rootView;

        ImageButton mbtnCloseFragment = rootView.findViewById(R.id.btnCloseFragment);
        mbtnCloseFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).closeFragment();
            }
        });

        //  initiate the edit text
        startTime = rootView.findViewById(R.id.startTimePick);
        startTime.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE));
        // perform click event listener on edit text
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                startHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                startMinute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mStartTimePicker;
                mStartTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedStartHour, int selectedStartMinute) {
                        if (selectedStartMinute == 0) {
                            startTime.setText(selectedStartHour + ":00");
                        } else if (selectedStartMinute < 10) {
                            if (selectedStartHour < 10) {
                                startTime.setText("0" + selectedStartHour + ":0" + selectedStartMinute);
                            }
                        } else {
                            startTime.setText(selectedStartHour + ":" + selectedStartMinute);
                        }
                    }
                }, startHour, startMinute, true);//Yes 24 hour time
                mStartTimePicker.show();
            }
        });

        endTime = rootView.findViewById(R.id.endTimePick);
        endTime.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE));
        // perform click event listener on edit text
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                endHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                endMinute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mEndTimePicker;
                mEndTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedEndHour, int selectedEndMinute) {
                        if (selectedEndMinute == 0) {
                            endTime.setText(selectedEndHour + ":00");
                        } else if (selectedEndMinute < 10) {
                            if (selectedEndHour < 10) {
                                endTime.setText("0" + selectedEndHour + ":0" + selectedEndMinute);
                            }
                        } else {
                            endTime.setText(selectedEndHour + ":" + selectedEndMinute);
                        }
                    }
                }, endHour, endMinute, true);//Yes 24 hour time
                mEndTimePicker.show();
            }
        });

        Spinner spinner = (Spinner) rootView.findViewById(R.id.timeIntervalPick);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.listtimer, R.layout.spinner_support);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        meditConfirm_button = rootView.findViewById(R.id.editConfirm_button);
        meditConfirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String.valueOf(startHour);
                String.valueOf(endHour);
                String.valueOf(R.array.valueListTimer);
                new UploadAlarmDataToServer().execute();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    /*@Override*/
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*parent.getItemAtPosition(position);*/
        String[] valueListTimer = getResources().getStringArray(R.array.valueListTimer);
        value = valueListTimer[(int) parent.getItemAtPosition(position)];
        Log.e("Value of Spinner", value);
    }

    /*@Override*/
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class UploadAlarmDataToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            progDailog = new ProgressDialog(getContext());
            progDailog.setMessage("Menyimpan...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(false);
            progDailog.setCanceledOnTouchOutside(false);
            progDailog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String data = null;
            try {
                data = sendDataTimer();
            } catch (Exception e) {
                e.printStackTrace();
                data = "Gagal";
            }
            return data;
        }

        public String sendDataTimer() throws Exception {
            URL url = new URL("https://ius.mobile.indoserver.web.id/alarmData.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });
            entity.addPart("startHour", new StringBody(startHour + ""));
            entity.addPart("endHour", new StringBody(endHour + ""));
            entity.addPart("intervalWaktu", new StringBody(value + ""));

            totalSize = entity.getContentLength();
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.addRequestProperty("Content-length", totalSize + "");
            con.addRequestProperty(entity.getContentType().getName(), entity.getContentType().getValue());

            OutputStream os = con.getOutputStream();
            entity.writeTo(con.getOutputStream());
            os.close();
            con.connect();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            progDailog.dismiss();
            if (result != null) {
                Log.e("UPLOAD", result);
                if (result.equalsIgnoreCase("OK")) {
                    Toast.makeText(getContext(), "Data Alarm tersimpan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), MainActivity.class));
                } /*else
                    Snackbar.make(rootview.findViewById(android.R.id.content), result, Snackbar.LENGTH_LONG).show();*/
            }
            super.onPostExecute(result);
        }
    }

}