package com.traintimes.app.instantdelayrepay.Session;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.appevents.AppEventsLogger;
import com.onesignal.OneSignal;
import com.traintimes.app.instantdelayrepay.Objects.User;
import com.facebook.FacebookSdk;
import com.google.firebase.FirebaseApp;

import java.util.Calendar;

/**
 * Created by GeeksEra on 4/26/2018.
 */

public class MyApplication extends Application {

    User user = null;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    public Boolean getExpire() {
        return pref.getBoolean("Expire", false);  // getting boolean
    }

    public void setExpire(Boolean Check) {
        editor.putBoolean("Expire", Check);  // Saving string
        editor.commit(); // commit changes
    }


    public Boolean getCong() {
        return pref.getBoolean("Message", false);  // getting boolean
    }

    public void setCong(Boolean Check) {
        editor.putBoolean("Message", Check);  // Saving string
        editor.commit(); // commit changes
    }


    public Boolean getPurchase() {
        return pref.getBoolean("Purchase", false);  // getting boolean
    }

    public void setPurchase(Boolean Check) {
        editor.putBoolean("Purchase", Check);  // Saving string
        editor.commit(); // commit changes
    }

    public long getStartDate() {
        return pref.getLong("StartDate", 0);  // getting boolean
    }

    public void setStartDate(long startDate) {
        editor.putLong("StartDate", startDate);  // Saving string
        editor.commit(); // commit changes
    }

    @Override
    public void onCreate() {
        super.onCreate();


        pref = getApplicationContext().getSharedPreferences("MyPrefTrainAPP", MODE_PRIVATE);
        editor = pref.edit();


        long ms1 = Calendar.getInstance().getTime().getTime();
        long ms2 = getStartDate();
        long totalsec = (ms2 - ms1) / 1000;
        int days = (int) ((totalsec / (1000 * 60 * 60 * 24)) % 7);

        if (days == 31) {
            setExpire(true);
        }
        FirebaseApp.initializeApp(this);
        AppEventsLogger.activateApp(this);
        FacebookSdk.sdkInitialize(this);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

