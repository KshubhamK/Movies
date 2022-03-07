package com.shubham.movies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppCommonMethods {
    public static String convertDateFormat(String currentTimeFormat, String expectedTimeFormat, String time) {
        if (time == null) {
            return "";
        }
        String newTime = null;
        SimpleDateFormat actual = new SimpleDateFormat(currentTimeFormat);
        SimpleDateFormat target = new SimpleDateFormat(expectedTimeFormat);
        Date date;
        try {
            date = actual.parse(time);
            newTime = target.format(date);
            return newTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void putStringPref(String key, String value, Context context) {
        if(context!=null) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public static String getStringPref(String key, Context context) {
        if(context!=null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(key, "");
        }else {
            return "";
        }
    }

    public static String convertFromMinutesToHours(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (totalMinutes / 60) + "h " + minutes + "m";
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        return formatter.format(Double.parseDouble(amount));
    }
}
