package com.nephat.truhouse.admin;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.nephat.truhouse.R;

public class AdminWebViewActivity extends AppCompatActivity {

    private static final String TAG = "AdminWebViewActivity";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_web_view);

        mWebView = findViewById(R.id.webView);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://mail.google.com/mail/u/3/#inbox");


    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()){
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }

    }

    private class Callback extends WebViewClient {

    }
}