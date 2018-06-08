package com.traintimes.app.instantdelayrepay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.traintimes.app.instantdelayrepay.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);
        setContentView(R.layout.activity_main);


        findViewById(R.id.Signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Signup_Op.class));
            }
        });

        findViewById(R.id.Login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }


    @Override
    protected void onPause() {
        // Whenever this activity is paused (i.e. looses focus because another activity is started etc)
        // Override how this activity is animated out of view
        // The new activity is kept still and this activity is pushed out to the left
        overridePendingTransition(R.anim.hold, R.anim.pull_out_to_left);
        super.onPause();
    }
}
