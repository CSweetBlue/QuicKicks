package com.example.christiansweet.adidasbuyer;


import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
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
import java.util.Arrays;

/**
 * Created by Christian Sweet on 10/22/2016.
 */

public class shoechoiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoechoice_size);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());


        //generateHTML();
        //File file = new File(this.getFilesDir().getPath() + "/captcha.html");
        //myWebView.loadUrl("file:///" + file);

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

                    ImageView shoePicture = (ImageView)findViewById(R.id.imageView);
                    shoePicture.setImageResource(R.drawable.a280647);

                }
                if(pos == 1)
                {
                    String[] items1 = new String[]{"9.5", "11", "12.5", "15"};
                    Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(shoechoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, items1);
                    dropdown1.setAdapter(adapter1);

                    ImageView shoePicture = (ImageView)findViewById(R.id.imageView);
                    shoePicture.setImageResource(R.drawable.bb4274);
                }
                if(pos == 2)
                {
                    String[] items1 = new String[]{"4", "4.5", "5", "5.5", "6.5", "7", "7.5", "8", "8.5", "9", "9.5"};
                    Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(shoechoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, items1);
                    dropdown1.setAdapter(adapter1);

                    ImageView shoePicture = (ImageView)findViewById(R.id.imageView);
                    shoePicture.setImageResource(R.drawable.c77124);
                }
                if(pos == 3)
                {
                    String[] items1 = new String[]{"4", "4.5", "5", "5.5", "6.5", "7", "7.5", "8", "8.5", "9", "9.5"};
                    Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(shoechoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, items1);
                    dropdown1.setAdapter(adapter1);

                    ImageView shoePicture = (ImageView)findViewById(R.id.imageView);
                    shoePicture.setImageResource(R.drawable.s81835);
                }

                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.abs_layout);
                getSupportActionBar().setTitle("Kicks");
            }


            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

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


    public void generateHTML() {
        Client c = new Client();
        String siteKey = c.getServerData()[0];


        String filename = "captcha.html";
        String output = "<html> <head> <script src='https://www.google.com/recaptcha/api.js'></script> </head> <body> <form>" +
                "<div class=\"g-recaptcha\" data-sitekey=\" " + siteKey + "\"></div>" +
                "</form> </body> </html>";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(output.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
