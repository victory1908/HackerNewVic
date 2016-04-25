package com.vic.hackernew.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by vic on 25-Apr-16.
 */
public class DateTimeFunction {

    public static String formatDateTime(long time) {
        DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        long milliseconds = time * 1000L; //is this *1000 required? if not, please remove it.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        TimeZone timeZone = TimeZone.getDefault();
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(calendar.getTime());
    }

}
