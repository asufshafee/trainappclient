package com.traintimes.app.instantdelayrepay;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.traintimes.app.instantdelayrepay.util.AppUtils;
import com.traintimes.app.instantdelayrepay.utils.AppUtil;
import com.traintimes.app.instantdelayrepay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView Tittle;
    private FirebaseAuth firebaseAuth;
    EditText Email, Password;
    Button Login, Forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);
        setContentView(R.layout.activity_login);


        Login = findViewById(R.id.Login);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Forget = findViewById(R.id.ForgetPassword);
        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        } else {
//            finish();
//            Intent intent = new Intent(Login.this, MainActivity.class);
//            startActivity(intent);
        }


        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        }


        Tittle = findViewById(R.id.Tittle_Back);
        Tittle.setText("Login");

        findViewById(R.id.Beck).setVisibility(View.VISIBLE);
        findViewById(R.id.Beck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.super.onBackPressed();
            }
        });


        findViewById(R.id.Login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Password.getVisibility() == View.GONE) {
                    if (AppUtil.isNull(Email.getText().toString())) {
                        Email.setError("Email id Required");
                    } else if (!AppUtil.isValidEmailId(Email.getText().toString())) {
                        Email.setError("Enter Valid Email id");
                    } else {
                        final Dialog progressDialog = AppUtils.LoadingSpinner(com.traintimes.app.instantdelayrepay.Login.this);
                        progressDialog.show();
                        FirebaseAuth.getInstance().sendPasswordResetEmail(Email.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Password.setVisibility(View.VISIBLE);
                                            Tittle.setText("Login");
                                            Login.setText("Login");
                                            Forget.setText("Forget Password");
                                            findViewById(R.id.Line).setVisibility(View.VISIBLE);
                                            findViewById(R.id.Line).setVisibility(View.VISIBLE);
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Check Your Email to Reset Password", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                } else {
                    if (validation()) {
                        if (AppUtil.isInternetOn(Login.this)) {
                            userLogin();
                        }
                    }
                }


            }
        });

        findViewById(R.id.ForgetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Password.getVisibility() == View.VISIBLE) {
                    Password.setVisibility(View.GONE);
                    Tittle.setText("Reset Password");
                    Login.setText("Reset");
                    Forget.setText("Login");
                    findViewById(R.id.Line).setVisibility(View.INVISIBLE);
                } else {
                    Password.setVisibility(View.VISIBLE);
                    Tittle.setText("Login");
                    Login.setText("Login");
                    Forget.setText("Forget Password");
                    findViewById(R.id.Line).setVisibility(View.VISIBLE);

                }


            }
        });
    }


    private boolean validation() {

        if (AppUtil.isNull(Email.getText().toString())) {
            Email.setError("Email id Required");
            return false;
        } else if (!AppUtil.isValidEmailId(Email.getText().toString())) {
            Email.setError("Enter Valid Email id");
            return false;
        }

        if (AppUtil.isNull(Password.getText().toString())) {
            Password.setError("Password Required");
            return false;
        } else if (Password.getText().toString().length() <= 5) {
            Password.setError("Password length cant be less than 5 character");
            return false;
        }
        return true;
    }

    private void userLogin() {


        final Dialog progressDialog = AppUtils.LoadingSpinner(com.traintimes.app.instantdelayrepay.Login.this);
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                            finish();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Wrong Email id password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        // Whenever this activity is paused (i.e. looses focus because another activity is started etc)
        // Override how this activity is animated out of view
        // The new activity is kept still and this activity is pushed out to the left
//        overridePendingTransition(R.anim.hold, R.anim.pull_out_to_left);
        super.onPause();
    }

}
