package com.traintimes.app.instantdelayrepay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.traintimes.app.instantdelayrepay.Objects.User;
import com.traintimes.app.instantdelayrepay.R;
import com.traintimes.app.instantdelayrepay.Session.MyApplication;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collection;

public class Signup_Op extends AppCompatActivity {


    TextView Tittle;
    GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();


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


        Tittle = findViewById(R.id.Tittle_Back);
        Tittle.setText("Sign up");
        findViewById(R.id.Beck).setVisibility(View.VISIBLE);
        findViewById(R.id.Beck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup_Op.super.onBackPressed();
            }
        });

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = findViewById(R.id.button_facebook_login);
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//                // ...
//            }
//        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        findViewById(R.id.Facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Collection<String> readPermissions = new ArrayList<>();
                readPermissions.add("public_profile");
                readPermissions.add("email");
                LoginManager.getInstance().logInWithReadPermissions(Signup_Op.this, readPermissions);
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });

            }
        });

        findViewById(R.id.Google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        findViewById(R.id.Email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 10) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.

            String Email = account.getEmail();
            String Name = account.getDisplayName();

            Intent intent = new Intent(getApplicationContext(), Signup.class);
            User user = new User();
            user.setName(Name);
            user.setEmail(Email);
            MyApplication myApplication = (MyApplication) getApplicationContext();
            myApplication.setUser(user);
            startActivity(intent);
            finish();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getApplicationContext(), "Error!!", Toast.LENGTH_LONG).show();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser userFirebase = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), Signup.class);
                            User user = new User();
                            user.setName(userFirebase.getDisplayName());
                            user.setEmail(userFirebase.getEmail());
                            MyApplication myApplication = (MyApplication) getApplicationContext();
                            myApplication.setUser(user);
                            startActivity(intent);
                            FirebaseAuth.getInstance().signOut();
                            finish();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Signup_Op.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
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
