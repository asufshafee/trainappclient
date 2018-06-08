package com.traintimes.app.instantdelayrepay.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Sachitt Jamankar on 9/18/2016.
 */
public class AppUtil {

    public static boolean isInternetOn(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                    // connected to the mobile provider's data plan
                    return true;
                }
            } else {

                // not connected to the internet
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isNull(Object str) {
        if (str == (null) || str.toString().equals("")
                || str.toString().equals(" ")
                || str.toString().trim().length() == 0
                || str.toString().trim().equals("null")) {
            return true;
        }
        return false;
    }

    public static boolean isValidMobileNo(String value) {
        boolean flag = false;
        try {
            String pattern = "^[789]\\d{9}$";
            if (Pattern.compile(pattern).matcher(value).matches()) {
                flag = true;
            } else {
                flag = false;
            }
            return flag;

        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            return flag;
        }
    }

    public static boolean isValidEmailId(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
