package com.example.christiansweet.adidasbuyer;

import com.example.christiansweet.adidasbuyer.Client;


import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Chris on 10/22/2016.
 */

public class LocalServer extends NanoHTTPD {

    private String key;

    public LocalServer() throws IOException {
        super(8080);
        Client c = new Client();
        key = c.getSiteKey();
    }

    @Override public Response serve(IHTTPSession session) {
        String output = "<html>   <head>    <script type=\"text/javascript\"> var onloadCallback = function() { grecaptcha.render('html_element', { 'sitekey' : '" + key + "'  }); }; </script>  </head>  <body bgcolor=\"#f9f9f9\">  <div id=\"html_element\"></div> <script src=\"https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit\"  async defer> </script> </body> </html>";

        String mime_type = NanoHTTPD.MIME_HTML;
        Method method = session.getMethod();
        String uri = session.getUri();
        System.out.println(method + " '" + uri + "' ");

        return new NanoHTTPD.Response( Response.Status.OK,mime_type,output);
    }


}
