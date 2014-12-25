// http://stackoverflow.com/questions/3283234/http-basic-authentication-in-java-using-httpclient

package com.mb.http;

import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpBasicAuth {

    public static void main(String[] args) {


        try {
            ///
            URL url = new URL ("http://localhost:7777/portalserver/login/loginDashboard.jsp?login_error=logout");
            ///URL url = new URL ("http://localhost:7777/portalserver/portals/dashboard/pages/index");

            String orig = "admin:admin";

            BASE64Encoder encoder = new BASE64Encoder();
            String encoding = encoder.encode(orig.getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            InputStream content = connection.getInputStream();
            BufferedReader in   =
                    new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}