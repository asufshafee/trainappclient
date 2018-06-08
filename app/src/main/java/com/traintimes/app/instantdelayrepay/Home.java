package com.traintimes.app.instantdelayrepay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.traintimes.app.instantdelayrepay.Fragments.About_Us;
import com.traintimes.app.instantdelayrepay.Fragments.Find_Delays;
import com.traintimes.app.instantdelayrepay.Fragments.My_Account;
import com.traintimes.app.instantdelayrepay.Fragments.My_Routes;
import com.traintimes.app.instantdelayrepay.Objects.User;
import com.traintimes.app.instantdelayrepay.Session.MyApplication;
import com.traintimes.app.instantdelayrepay.R;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Home extends AppCompatActivity {


    private NavigationTabStrip mTopNavigationTabStrip;

    TextView Tittle;

    ProgressDialog progressDialog;
    private DatabaseReference dbref;
    AHBottomNavigation bottomNavigation;

    MyApplication myApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);
        setContentView(R.layout.activity_home);


//        printHashKey(Home.this);
        myApplication = (MyApplication) getApplicationContext();
        Tittle = findViewById(R.id.Tittle_Back);
        mTopNavigationTabStrip = findViewById(R.id.nts);
        progressDialog = new ProgressDialog(Home.this);
        progressDialog.show();

        findViewById(R.id.Beck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Home.super.onBackPressed();
                Invisibileback();

            }
        });


        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab1, R.drawable.delay_train_icon, R.color.black);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab2, R.drawable.about_us, R.color.black);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab3, R.drawable.my_routes, R.color.black);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab4, R.drawable.my_acccount, R.color.black);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(Color.parseColor("#E6890E"));

        bottomNavigation.setInactiveColor(Color.parseColor("#000000"));
        bottomNavigation.setTitleTextSize(18, 15);

        setUI();
        GetUSerDate();
    }

    private void setUI() {

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...

                switch (position) {
                    case 0:
                        ShowFragment(new Find_Delays());
                        Invisibileback();

                        break;
                    case 1:
                        ShowFragment(new About_Us());
                        Invisibileback();

                        break;
                    case 2:
                        ShowFragment(new My_Routes());
                        Invisibileback();

                        break;
                    case 3:
                        mCurrentFragment = new My_Account();
                        ShowFragment(mCurrentFragment);
                        Invisibileback();

                        break;
                }

                return true;
            }
        });


        mTopNavigationTabStrip.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {


            }

            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });


        if (myApplication.getExpire()) {
            bottomNavigation.disableItemAtPosition(0);
            bottomNavigation.disableItemAtPosition(1);
            bottomNavigation.disableItemAtPosition(2);
            bottomNavigation.setCurrentItem(3);
            mTopNavigationTabStrip.setTabIndex(3, true);
            ShowFragment(new My_Account());

        } else {
            bottomNavigation.setCurrentItem(0);
            mTopNavigationTabStrip.setTabIndex(0, true);
            ShowFragment(new Find_Delays());

        }


    }

    public void ChangeTittle(String Name) {
        Tittle.setText(Name);
    }

    public void ShowFragment(Fragment fragment) {
        removeActiveCenterFragments();
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).commit();
    }

    public void ShowFragment1(Fragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).commit();
    }

    public void ShowFragemnt(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, "Details").setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).addToBackStack("").commit();

    }

    public static String getOnlyStrings(String s) {
        Pattern pattern = Pattern.compile("[^a-z A-Z]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }

    public void GetUSerDate() {


        dbref = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                MyApplication myApplication = (MyApplication) getApplicationContext();
                myApplication.setUser(user);
                progressDialog.dismiss();


                if (!myApplication.getCong()) {
                    new cn.pedant.SweetAlert.SweetAlertDialog(Home.this, cn.pedant.SweetAlert.SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Congratulations")
                            .setContentText("you got 30 days free trial!")
                            .show();
                    myApplication.setCong(true);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Invisibileback();
        super.onBackPressed();

    }

    public void Visibileback() {
        findViewById(R.id.Beck).setVisibility(View.VISIBLE);
    }

    public void Invisibileback() {
        findViewById(R.id.Beck).setVisibility(View.INVISIBLE);
    }

    private void removeActiveCenterFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        List<Fragment> activeCenterFragments = new ArrayList<Fragment>();
        activeCenterFragments = getSupportFragmentManager().getFragments();
        if (activeCenterFragments.size() > 0) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            for (Fragment activeFragment : activeCenterFragments) {
                fragmentTransaction.remove(activeFragment);
            }
            activeCenterFragments.clear();
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onPause() {
        // Whenever this activity is paused (i.e. looses focus because another activity is started etc)
        // Override how this activity is animated out of view
        // The new activity is kept still and this activity is pushed out to the left
//        overridePendingTransition(R.anim.hold, R.anim.pull_out_to_left);
        super.onPause();
    }

    My_Account mCurrentFragment;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("", "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        if (mCurrentFragment != null) {
            mCurrentFragment.onActivityResult(requestCode, resultCode, data);
        }

    }


    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("HASHCODE", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("HASHCODE", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("HASHCODE", "printHashKey()", e);
        }
    }
}
