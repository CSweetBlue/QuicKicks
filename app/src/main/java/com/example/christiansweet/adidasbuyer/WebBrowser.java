package com.example.christiansweet.adidasbuyer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by Chris on 10/23/2016.
 */

public class WebBrowser extends AppCompatActivity {

    private WebView myWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_final);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        myWebView = (WebView) findViewById(R.id.webView2);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.loadUrl("https://cp.adidas.com/web/eCom/en_US/loadsignin?target=account");

    }
}
