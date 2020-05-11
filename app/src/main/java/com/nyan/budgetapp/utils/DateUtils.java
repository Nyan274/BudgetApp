package com.nyan.budgetapp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getDateTimeString(long dateTime) {
        Date date = new Date(dateTime);
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
        return dateFormat.format(date);
    }
}
