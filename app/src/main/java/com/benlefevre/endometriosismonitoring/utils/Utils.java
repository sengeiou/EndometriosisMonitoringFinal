package com.benlefevre.endometriosismonitoring.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    /**
     * Verifies if the device has a network access and return a boolean.
     */
    public static boolean isNetworkAccessEnabled(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Format a date in a String with a specific pattern
     * @param date the date that must be formatted
     * @return the String with the right value
     */
    public static String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        return dateFormat.format(date);
    }

}
