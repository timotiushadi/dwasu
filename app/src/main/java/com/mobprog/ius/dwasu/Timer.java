package com.mobprog.ius.dwasu;

import android.content.SharedPreferences;
import android.os.CountDownTimer;

import java.util.Calendar;

public abstract class Timer extends CountDownTimer {

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */



    public Timer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void createNewTimer(int jamstart, int jamselesai, int interval){

        int checkCurrentTime = Calendar.getInstance().HOUR_OF_DAY;

        while ((checkCurrentTime == jamstart) && (checkCurrentTime == jamselesai) && (checkCurrentTime > jamselesai)) {
            new CountDownTimer(interval, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Notification notification = new Notification();
                    notification.sendNotification();
                }
            };
            onFinish();
        }
    }
}
