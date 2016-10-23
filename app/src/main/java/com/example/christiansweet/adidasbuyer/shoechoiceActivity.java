package com.example.christiansweet.adidasbuyer;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Christian Sweet on 10/22/2016.
 */

public class shoechoiceActivity extends AppCompatActivity {

    private WebView myWebView;
    private LocalServer l;
    private ImageView shoePicture;
    private String captchaResponse = "";
    private Button button;
    private Spinner dropdown;
    private Spinner dropdown1;

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

        button = (Button) findViewById(R.id.button);

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
                                    if (!captchaResponse.equals("")) {
                                        button.setClickable(true);
                                    }
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
        dropdown = (Spinner)findViewById(R.id.spinner);
        //dropdown.setGravity(Gravity.CENTER_HORIZONTAL);
        String[] items = new String[]{"Adilette Slides", "Ultra Boost Uncaged Shoes", "Superstar Foundation Shoes", "NMD_C1 Trail Shoes"};
        final String[] skus = new String[]{"280647", "BB4274", "C77124", "S81835"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, View view, final int pos, long l) {


                    new AsyncTask<Void, Void, Void>() {

                        String[] items;

                        @Override
                        protected Void doInBackground(Void... voids) {
                            try {
                                String[] data = Client.getSizes(skus[pos]).replaceAll("[{}]", "").split(", ");
                                items = new String[data.length];
                                for (int i = 0 ; i < data.length; i++) {
                                    String[] vals = data[i].split("=");
                                    items[i] = "Size: " + vals[0] + ", Stock Available: " + vals[1];
                                }

                            } catch (IOException e) {
                                items = new String[]{};
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            dropdown1 = (Spinner)findViewById(R.id.spinner1);
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(shoechoiceActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
                            dropdown1.setAdapter(adapter1);
                            //((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);

                        }
                    }.execute();

                switch (pos) {
                    case 0: shoePicture.setImageResource(R.drawable.a280647); break;
                    case 1: shoePicture.setImageResource(R.drawable.bb4274); break;
                    case 2: shoePicture.setImageResource(R.drawable.c77124); break;
                    case 3: shoePicture.setImageResource(R.drawable.s81835); break;
                }

                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.abs_layout);
            }


            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button(skus);
            }
        });

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    l = new LocalServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        l.stop();
    }

    private void button(final String[] skus) {
        myWebView.loadUrl("javascript:HtmlViewer.printData(grecaptcha.getResponse());");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                boolean result;
                try {
                    double size = Double.parseDouble(((String) dropdown1.getSelectedItem()).split(", ")[0].split(": ")[1]);
                    result = Client.addToCart(captchaResponse, skus[dropdown.getSelectedItemPosition()], size);
                } catch (Exception e) {
                    e.printStackTrace();
                    result = false;
                }

                final boolean finalResult = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (finalResult) {
                            // open the web browser thing to cart page logged in
                            new AlertDialog.Builder(shoechoiceActivity.this)
                                    .setMessage("Success! Will now open you to your account page to checkout.").setPositiveButton("OK", null)
                                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialogInterface) {

                                            // open the web browser or something here
                                            Intent intent = new Intent(shoechoiceActivity.this, WebBrowser.class);
                                            shoechoiceActivity.this.startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                        }
                        else {
                            // display error, refresh captcha
                            new AlertDialog.Builder(shoechoiceActivity.this)
                                        .setMessage("Failed! Try solving the captcha again and maybe selecting a different size. \n\nWould you like to open your cart anyways?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // open the web browser or something here
                                            Intent intent = new Intent(shoechoiceActivity.this, WebBrowser.class);
                                            shoechoiceActivity.this.startActivity(intent);
                                            finish();
                                        }

                                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    myWebView.loadUrl("http://127.0.0.1:8080");
                                    captchaResponse = "";
                                }
                            }).show();
                        }
                    }
                });




            }
        });
        t.start();

    }


}
