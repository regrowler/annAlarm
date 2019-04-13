package com.ikosmov.annnew2019;

public class AlarmInfoNow {
    int hour, minute;
    int year;
    int month;
    int day;
    int typetask;
    int typering;
    public int id=-1;
    boolean isOn = false;

    public AlarmInfoNow() {
    }

    public AlarmInfoNow(
            int year,
            int month,
            int day,
            int hour,
            int minute,
            int typetask,
            int typering) {
        this.hour = hour;
        this.minute = minute;
        this.typetask = typetask;
        this.typering = typering;

        this.year = year;
        this.month = month;
        this.day = day;
    }


}
