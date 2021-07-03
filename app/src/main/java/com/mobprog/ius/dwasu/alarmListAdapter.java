package com.mobprog.ius.dwasu;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class alarmListAdapter extends RecyclerView.Adapter<alarmListAdapter.alarmListHolder>{

    public final ArrayList<MyListDataTimer> myListDataTimers;

    public alarmListAdapter(ArrayList<MyListDataTimer> myListDataTimerArrayList) {
        this.myListDataTimers = myListDataTimerArrayList;
    }

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
    }

    @Override
    public int getItemCount() {

        return myListDataTimers.size();
    }

    public static class alarmListHolder extends RecyclerView.ViewHolder {

        public TextView mintervalTime;
        public TextView mtimeWork;

        public alarmListHolder(View itemView) {
            super(itemView);
            mintervalTime = itemView.findViewById(R.id.intervalTime);
            mtimeWork = itemView.findViewById(R.id.timeWork);

        }
    }
}

