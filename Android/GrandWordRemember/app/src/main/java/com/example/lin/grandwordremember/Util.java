package com.example.lin.grandwordremember;

import java.util.Calendar;

public class Util {
    static public String getTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + second;
    }
}
