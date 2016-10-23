package com.example.christiansweet.adidasbuyer;


import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import fi.iki.elonen.ServerRunner;

/**
 * Created by Christian Sweet on 10/22/2016.
 */

public class shoechoiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private WebView myWebView;
    private LocalServer l;
    private ImageView shoePicture;
    private String captchaResponse = "";

    class MyJavaScriptInterface {

        @android.webkit.JavascriptInterface
        public void printData(String data) {
            captchaResponse = data;
        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoechoice_size);
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlViewer");


        //generateHTML();
        //File file = new File(this.getFilesDir().getPath() + "/captcha.html");
        //myWebView.loadUrl("file:///" + file);
        myWebView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                // here, on the last request, then return the token
                if (request.getUrl().toString().contains("userverify")) {
                    shoechoiceActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    myWebView.loadUrl("javascript:HtmlViewer.printData(grecaptcha.getResponse());");
                                    myWebView.loadUrl("javascript:HtmlViewer.printData(grecaptcha.getResponse());");
                                    myWebView.loadUrl("javascript:HtmlViewer.printData(grecaptcha.getResponse());");

                                    // here, do a check on the button for ATC and enable it
                                }
                            }, 500);
                        }
                    });
                }
                return super.shouldInterceptRequest(view, request);
            }
        });
        myWebView.getSettings().setJavaScriptEnabled(true);

        shoePicture = (ImageView)findViewById(R.id.imageView);
        Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        dropdown.setGravity(Gravity.CENTER_HORIZONTAL);
        String[] items = new String[]{"Adilette Slides", "Ultra Boost Uncaged Shoes", "Superstar Foundation Shoes", "NMD_C1 Trail Shoes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
//        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);
//            }
//        });
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if(pos == 0)
                {
                    String[] items1 = new String[]{"9", "10", "11", "12", "13", "14", "15"};
                    Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(shoechoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, items1);
                    dropdown1.setAdapter(adapter1);
                    ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);


                    shoePicture.setImageResource(R.drawable.a280647);

                }
                if(pos == 1)
                {
                    String[] items1 = new String[]{"9.5", "11", "12.5", "15"};
                    Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(shoechoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, items1);
                    dropdown1.setAdapter(adapter1);


                    shoePicture.setImageResource(R.drawable.bb4274);
                }
                if(pos == 2)
                {
                    String[] items1 = new String[]{"4", "4.5", "5", "5.5", "6.5", "7", "7.5", "8", "8.5", "9", "9.5"};
                    Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(shoechoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, items1);
                    dropdown1.setAdapter(adapter1);


                    shoePicture.setImageResource(R.drawable.c77124);
                }
                if(pos == 3)
                {
                    String[] items1 = new String[]{"4", "4.5", "5", "5.5", "6.5", "7", "7.5", "8", "8.5", "9", "9.5"};
                    Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(shoechoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, items1);
                    dropdown1.setAdapter(adapter1);

                    shoePicture = (ImageView)findViewById(R.id.imageView);
                    shoePicture.setImageResource(R.drawable.s81835);
                }

                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.abs_layout);
                getSupportActionBar().setTitle("Kicks");
            }


            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                l = new LocalServer();
                try {
                    l.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                myWebView.loadUrl("http://127.0.0.1:8080");
            }
        }.execute();

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        if(pos == 0)
        {
            String[] items1 = new String[]{"9", "10", "11", "12", "13", "14", "15"};
            Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
            dropdown1.setAdapter(adapter1);

        }
        if(pos == 1)
        {
            String[] items1 = new String[]{"9.5", "11", "12.5", "15"};
            Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
            dropdown1.setAdapter(adapter1);
        }
        if(pos == 2)
        {
            String[] items1 = new String[]{"4", "4.5", "5", "5.5", "6.5", "7", "7.5", "8", "8.5", "9", "9.5"};
            Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
            dropdown1.setAdapter(adapter1);
        }

    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        String[] items1 = new String[]{""};
        Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);
    }


    @Override
    protected void onDestroy() {
        l.stop();
    }


}
