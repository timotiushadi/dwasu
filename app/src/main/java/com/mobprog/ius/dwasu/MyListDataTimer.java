package com.mobprog.ius.dwasu;

public class MyListDataTimer {
    private String startHour;
    private String endHour;
    private String intervalWaktu;
    private String position;

    public MyListDataTimer(String startHour, String endHour, String intervalWaktu, String position) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.intervalWaktu = intervalWaktu;
        this.position = position;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getIntervalWaktu() {
        return intervalWaktu;
    }

    public void setIntervalWaktu(String intervalWaktu) {
        this.intervalWaktu = intervalWaktu;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
