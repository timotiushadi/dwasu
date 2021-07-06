package com.mobprog.ius.dwasu;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;


public class alarmListAdapter extends RecyclerView.Adapter<alarmListAdapter.alarmListHolder>{

    public final ArrayList<MyListDataTimer> myListDataTimers;

    public alarmListAdapter(ArrayList<MyListDataTimer> myListDataTimerArrayList) {
        this.myListDataTimers = myListDataTimerArrayList;
    }

    @NonNull
    @Override
    public alarmListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mItemView = layoutInflater.inflate(R.layout.alarm_item, parent, false);
        return new alarmListHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(alarmListAdapter.alarmListHolder holder, int position) {
        final MyListDataTimer myListData = myListDataTimers.get(position);
        holder.mintervalTime.setText("Interval " + myListData.getIntervalWaktu() + " menit");
        holder.mtimeWork.setText(myListData.getStartHour() + ":00 - " + myListData.getEndHour() +":00");
        holder.mcheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long milisinfuture = Long.parseLong(myListData.getIntervalWaktu())*60000;
                int convertedStartHour = Integer.parseInt(myListData.getStartHour());
                int convertedEndHour = Integer.parseInt(myListData.getEndHour());
                if (!holder.mcheckbox.isChecked()){
                    if ((Calendar.HOUR_OF_DAY >= convertedStartHour) && (Calendar.HOUR_OF_DAY <= convertedEndHour)){
                        new CountDownTimer(milisinfuture, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                holder.mlastMinutesToCall.setText("In " + (millisUntilFinished/60000) +" minutes");
                                Log.e("onTick","Timer jalan");
                            }

                            @Override
                            public void onFinish() {
                                Log.e("onFinish","Timer selesai");
                                if (holder.mcheckbox.isChecked()){
                                    holder.mcheckbox.toggle();
                                    holder.mintervalTime.setText("");
                                    try {
                                        new Notification().sendNotification(Calendar.HOUR_OF_DAY, "Timer is Done", "Time to get YOURS");
                                    }
                                    catch (Exception e){
                                        Log.e("Notif", "Notif Error");
                                    }
                                }
                            }
                        }.start();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return myListDataTimers.size();
    }

    public static class alarmListHolder extends RecyclerView.ViewHolder {

        public TextView mintervalTime;
        public TextView mtimeWork;
        public CheckBox mcheckbox;
        public TextView mlastMinutesToCall;

        public alarmListHolder(View itemView) {
            super(itemView);
            mintervalTime = itemView.findViewById(R.id.intervalTime);
            mtimeWork = itemView.findViewById(R.id.timeWork);
            mcheckbox = itemView.findViewById(R.id.checkbox);
            mlastMinutesToCall = itemView.findViewById(R.id.lastMinutesToCall);

        }
    }
}

