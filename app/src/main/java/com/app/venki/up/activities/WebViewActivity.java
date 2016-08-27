package com.app.venki.up.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.venki.up.R;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    WebView webView;
    CustomWebViewClient customWebViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String link = getIntentWebLink();

        initWebView(link);
//        createCustomWebView(link);
    }

    private String getIntentWebLink() {
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        return link;
    }

    private void initWebView(String link) {
        webView = (WebView) findViewById(R.id.webView);
        createCustomWebView(link);
        webView.setWebViewClient(customWebViewClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
    }

    private void createCustomWebView(String link) {
        customWebViewClient = new CustomWebViewClient();
        customWebViewClient.onLoadResource(webView, link);
    }


    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url); //try return false;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.i("laborLaws", "onLoadResource: web page is loading");
        }
    }
}