package com.mobprog.ius.dwasu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class alarmListAdapter extends RecyclerView.Adapter<alarmListAdapter.alarmListHolder> {

    private ArrayList<String> mArrayTimeWork = new ArrayList<>();
    private ArrayList<String> mArraylastMinutestoCall = new ArrayList<>();
    private ArrayList<String> mArraynextTimeRemindin = new ArrayList<>();
    private ArrayList<String> mArrayremindTimeEvery = new ArrayList<>();
    private Context context;

    public alarmListAdapter(ArrayList<String> mArrayTimeWork, ArrayList<String> mArraylastMinutestoCall, ArrayList<String> mArraynextTimeRemindin, ArrayList<String> mArrayremindTimeEvery, Context context) {
        this.mArrayTimeWork = mArrayTimeWork;
        this.mArraylastMinutestoCall = mArraylastMinutestoCall;
        this.mArraynextTimeRemindin = mArraynextTimeRemindin;
        this.mArrayremindTimeEvery = mArrayremindTimeEvery;
        this.context = context;
    }

    @NonNull
    @Override
    public alarmListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
//        RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull alarmListAdapter.alarmListHolder holder, int position) {

//        holder.mtimeWork.setText();

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class alarmListHolder extends RecyclerView.ViewHolder{

        public TextView mtimeWork;
        public TextView mremindTimeEvery;
        public TextView mlastMinutestoCall;
        public TextView mnextTimeReminding;
        RelativeLayout mitemRelativeLayout;
//        final alarmListAdapter mAdapter;

        public alarmListHolder(@NonNull View itemView) {
            super(itemView);
            mtimeWork = itemView.findViewById(R.id.timeWork);
            mremindTimeEvery = itemView.findViewById(R.id.remindTimeEvery);
            mlastMinutestoCall = itemView.findViewById(R.id.lastMinutesToCall);
            mnextTimeReminding = itemView.findViewById(R.id.nextTimeReminding);
            mitemRelativeLayout = itemView.findViewById(R.id.itemRelativeLayout);
        }
    }
}

