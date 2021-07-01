package com.mobprog.ius.dwasu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;


public class alarmListAdapter extends RecyclerView.Adapter<alarmListAdapter.alarmListHolder>{

    private final LinkedList<String> mintervalTimeList;
    private LayoutInflater mInflater;

    public alarmListAdapter(Context context, LinkedList<String> mintervalTimeList) {
        mInflater = LayoutInflater.from(context);
        this.mintervalTimeList = mintervalTimeList;
    }


    @Override
    public alarmListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.alarm_item,
                parent, false);
        return new alarmListHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(alarmListAdapter.alarmListHolder holder, int position) {
        String mCurrent = mintervalTimeList.get(position);
        holder.mintervalTime.setText(mCurrent);
    }

    @Override
    public int getItemCount() {

        return mintervalTimeList.size();
    }

    public class alarmListHolder extends RecyclerView.ViewHolder {

        public final TextView mintervalTime;
        final alarmListAdapter mAdapter;

        public alarmListHolder(View itemView, alarmListAdapter adapter) {
            super(itemView);
            mintervalTime = itemView.findViewById(R.id.intervalTime);
            this.mAdapter = adapter;

        }
    }
}

