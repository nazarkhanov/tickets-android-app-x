package com.tickets.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Date {
    public static String format(Calendar calendar) {
        String format = "dd.MM.yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(calendar.getTime());
    }
}
