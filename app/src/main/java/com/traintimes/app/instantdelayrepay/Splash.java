package com.traintimes.app.instantdelayrepay;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.traintimes.app.instantdelayrepay.R;
import com.traintimes.app.instantdelayrepay.Session.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Splash extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        myApplication = (MyApplication) getApplicationContext();
        if (myApplication.getStartDate() == 0) {
            Date todayDate = Calendar.getInstance().getTime();
            myApplication.setStartDate(todayDate.getTime());

        }

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                FirebaseAuth firebaseAuth;
                firebaseAuth = FirebaseAuth.getInstance();

                if (firebaseAuth.getCurrentUser() != null) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else {
                    finish();
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                }


            }
        }, 3000);
    }

}
