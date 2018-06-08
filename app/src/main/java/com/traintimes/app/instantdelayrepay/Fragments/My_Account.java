package com.traintimes.app.instantdelayrepay.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.traintimes.app.instantdelayrepay.Session.MyApplication;
import com.traintimes.app.instantdelayrepay.Home;
import com.traintimes.app.instantdelayrepay.MainActivity;
import com.traintimes.app.instantdelayrepay.Objects.User;
import com.traintimes.app.instantdelayrepay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.traintimes.app.instantdelayrepay.util.IabHelper;
import com.traintimes.app.instantdelayrepay.util.IabResult;
import com.traintimes.app.instantdelayrepay.util.Inventory;
import com.traintimes.app.instantdelayrepay.util.Purchase;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Account extends Fragment {


    static final String TAG = "TrivialDrive";

    // Does the user have the premium upgrade?
    boolean mIsPremium = false;

    // Does the user have an active subscription to the infinite gas plan?
    boolean mSubscribedToInfiniteGas = false;

    // SKUs for our products: the p (non-consremium upgradeumable) and gas (consumable)
    static final String SKU_PREMIUM = "fullaccount";
    static final String SKU_GAS = "fullaccount";

    // SKU for our subscription (infinite gas)
    static final String SKU_INFINITE_GAS = "fullaccount";

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;

    // Graphics for the gas gauge


    // How many units (1/4 tank is our unit) fill in the tank.
    static final int TANK_MAX = 4;

    // Current amount of gas in tank, in units
    int mTank;

    // The helper object
    IabHelper mHelper;
    ProgressDialog progressDialog;


    public My_Account() {
        // Required empty public constructor
    }

    private DatabaseReference databaseReference;
    EditText Name, Email, Price;
    MyApplication myApplication;
    Button Subscribe;
    EditText Status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my__account, container, false);

        myApplication = (MyApplication) getActivity().getApplicationContext();
        Status = view.findViewById(R.id.Status);
        Subscribe = view.findViewById(R.id.Subscribe);
        CheckPurchase();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        /* base64EncodedPublicKey should be YOUR APPLICATION'S PUBLIC KEY
         * (that you got from the Google Play developer console). This is not your
         * developer public key, it's the *app-specific* public key.
         *
         * Instead of just storing the entire literal string here embedded in the
         * program,  construct the key at runtime from pieces or
         * use bit manipulation (for example, XOR with some other string) to hide
         * the actual key.  The key itself is not secret information, but we don't
         * want to make it easy for an attacker to replace the public key with one
         * of their own and then fake messages from the server.
         */
        String base64EncodedPublicKey = getString(R.string.key);

        // Some sanity checks to see if the developer (that's you!) really followed the
        // instructions to run this sample (don't put these checks on your app!)
        if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
            throw new RuntimeException("Please put your app's public key in MainActivity.java. See README.");
        }
        if (getActivity().getPackageName().startsWith("com.example")) {
            throw new RuntimeException("Please change the sample's package name! See README.");
        }

        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");
                progressDialog.dismiss();
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    SHowToast("Problem setting up in-app billing: " + result);

                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });


        Name = view.findViewById(R.id.Name);
        Email = view.findViewById(R.id.Email);
        Price = view.findViewById(R.id.Price);




        Name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    Price.requestFocus();
                    return true;
                }
                return false;
            }
        });

        try {
            MyApplication myApplication = (MyApplication) getActivity().getApplicationContext();
            Name.setText(myApplication.getUser().getName());
            Email.setText(myApplication.getUser().getEmail());
            Price.setText(myApplication.getUser().getPrice());
        } catch (Exception Ex) {

        }

        ((Home) getActivity()).ChangeTittle("My Account");

        view.findViewById(R.id.Logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();


            }
        });


        view.findViewById(R.id.Subscribe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String payload = "extra";
                progressDialog.show();
                mHelper.launchPurchaseFlow(getActivity(), SKU_GAS, RC_REQUEST,
                        mPurchaseFinishedListener, payload);
            }
        });


        view.findViewById(R.id.Save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setEmail(Email.getText().toString());
                user.setName(Name.getText().toString());
                user.setPrice(Price.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReference = FirebaseDatabase.getInstance().getReference("users");
                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    public void SHowToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        if (enter)
//        {
//            return CubeAnimation.create(CubeAnimation.LEFT, enter, 500);
//
//        }else {
//            return CubeAnimation.create(CubeAnimation.RIGHT, enter, 500);
//
//        }    }


    // Enables or disables the "please wait" screen.

//    void complain(String message) {
//        Log.e(TAG, "**** TrivialDrive Error: " + message);
//        alert("Error: " + message);
//    }

//    void alert(String message) {
//        AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
//        bld.setMessage(message);
//        bld.setNeutralButton("OK", null);
//        Log.d(TAG, "Showing alert dialog: " + message);
//        bld.create().show();
//    }


    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                SHowToast("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase premiumPurchase = inventory.getPurchase(SKU_GAS);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));

            // Do we have the infinite gas plan?
            Purchase infiniteGasPurchase = inventory.getPurchase(SKU_INFINITE_GAS);
            mSubscribedToInfiniteGas = (infiniteGasPurchase != null &&
                    verifyDeveloperPayload(infiniteGasPurchase));
            Log.d(TAG, "User " + (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE")
                    + " infinite gas subscription.");
            if (mSubscribedToInfiniteGas) mTank = TANK_MAX;

            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
            Purchase gasPurchase = inventory.getPurchase(SKU_GAS);
            if (gasPurchase != null) {
                Log.d(TAG, "We have gas. Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(SKU_GAS), mConsumeFinishedListener);
                return;
            }

//            updateUi();
//            setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };


    /**
     * Verifies the developer payload of a purchase.
     */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }


    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
//            progressDialog.dismiss();

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;


            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
//                myApplication.setPurchase(true);
//                CheckPurchase();
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                Log.d(TAG, "Consumption successful. Provisioning.");
                mTank = mTank == TANK_MAX ? TANK_MAX : mTank + 1;
//                saveData();
//                SHowToast("You filled 1/4 tank. Your tank is now " + String.valueOf(mTank) + "/4 full!");
            } else {
//                SHowToast("Error while consuming: " + result);
            }
//            updateUi();
//            setWaitScreen(false);
            Log.d(TAG, "End consumption flow.");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

//            SHowToast("Purchase successful.");
            progressDialog.dismiss();


            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
//                SHowToast("Error purchasing: " + result);
//                setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
//                SHowToast("Error purchasing. Authenticity verification failed.");
//                setWaitScreen(false);
                return;
            }
//            SHowToast("Purchase successful1lllll.");


            myApplication.setPurchase(true);
            CheckPurchase();

            if (purchase.getSku().equals(SKU_GAS)) {
                // bought 1/4 tank of gas. So consume it.
//                SHowToast("Purchase is gas. Starting gas consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }

        }
    };


    public void CheckPurchase() {
        if (myApplication.getPurchase()) {
            Subscribe.setEnabled(false);
            Status.setText("Full Account");
            Subscribe.setText("Subscribed");
            Subscribe.setVisibility(View.GONE);
        }

        if (myApplication.getExpire()) {
            Status.setText("Trial Expired");
        }
    }

}
