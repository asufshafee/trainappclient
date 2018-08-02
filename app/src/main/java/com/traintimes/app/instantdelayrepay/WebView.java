package com.traintimes.app.instantdelayrepay;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.traintimes.app.instantdelayrepay.R;
import com.traintimes.app.instantdelayrepay.util.AppUtils;

import im.delight.android.webview.AdvancedWebView;

public class WebView extends AppCompatActivity {


    private AdvancedWebView mWebView;
    TextView Tittle;
    Dialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        progressDialog = AppUtils.LoadingSpinner(WebView.this);

        Tittle = findViewById(R.id.Tittle_Back);
        Tittle.setText("Claim");

        findViewById(R.id.Beck).setVisibility(View.VISIBLE);
        findViewById(R.id.Beck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView.super.onBackPressed();
            }
        });

        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.loadUrl(getIntent().getStringExtra("Url"));
        mWebView.setListener(this, new AdvancedWebView.Listener() {

            @Override
            public void onPageStarted(String url, Bitmap favicon) {
                progressDialog.show();
            }

            @Override
            public void onPageFinished(String url) {

                progressDialog.dismiss();
            }

            @Override
            public void onPageError(int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

            }

            @Override
            public void onExternalPageRequest(String url) {

            }
        });

    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return;
        }
        // ...
        super.onBackPressed();
    }


}
