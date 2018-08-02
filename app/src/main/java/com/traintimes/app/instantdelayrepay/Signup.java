package com.traintimes.app.instantdelayrepay;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.traintimes.app.instantdelayrepay.Objects.User;
import com.traintimes.app.instantdelayrepay.Session.MyApplication;
import com.traintimes.app.instantdelayrepay.util.AppUtils;
import com.traintimes.app.instantdelayrepay.utils.AppUtil;
import com.traintimes.app.instantdelayrepay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {


    TextView Tittle;
    EditText Name, Email, Password, Price;
    private Dialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    MyApplication myApplication;
    CheckBox Terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);
        setContentView(R.layout.activity_signup2);

        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Price = findViewById(R.id.Price);
        Terms = findViewById(R.id.Terms);
        myApplication = (MyApplication) getApplicationContext();

        if (myApplication.getUser() != null) {
            Name.setText(myApplication.getUser().getName());
            Email.setText(myApplication.getUser().getEmail());
        }


        progressDialog = AppUtils.LoadingSpinner(Signup.this);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        }


        Tittle = findViewById(R.id.Tittle_Back);
        Tittle.setText("Sign up");
        findViewById(R.id.Beck).setVisibility(View.VISIBLE);
        findViewById(R.id.Beck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup.super.onBackPressed();
            }
        });


        findViewById(R.id.Signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validation()) {
                    if (Terms.isChecked()) {
                        if (AppUtil.isInternetOn(Signup.this)) {
                            registeruser();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Terms and Conditions are not Checked", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
    }

    private boolean validation() {


        if (AppUtil.isNull(Name.getText().toString())) {
            Name.setError("Name id Required");
            return false;
        }

        if (AppUtil.isNull(Email.getText().toString())) {
            Name.setError("Email id Required");
            return false;
        } else if (!AppUtil.isValidEmailId(Email.getText().toString())) {
            Email.setError("Enter Valid Email");
            return false;
        }

        if (AppUtil.isNull(Password.getText().toString())) {
            Password.setError("Password Required");
            return false;
        } else if (Password.getText().toString().length() <= 5) {
            Password.setError("Password length should be greater than 5 character");
            return false;
        }

        if (AppUtil.isNull(Price.getText().toString())) {
            Price.setError("Price id Required");
            return false;
        }
        return true;
    }


    private void registeruser() {


        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            finish();
                            Toast.makeText(Signup.this, "Success", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            User user = new User();
                            user.setEmail(Email.getText().toString());
                            user.setName(Name.getText().toString());
                            user.setPrice(Price.getText().toString());
                            databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            databaseReference = FirebaseDatabase.getInstance().getReference("users");
                            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                            startActivity(new Intent(getApplicationContext(), Home.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Email Already Exist!!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
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
