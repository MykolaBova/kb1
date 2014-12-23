package com.mb.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by m.bova on 12/23/2014.
 * Apache HttpClient - Tutorial
 * http://www.vogella.com/tutorials/ApacheHttpClient/article.html
 */
public class GetRequest2 {
    public static void main(String[] args) {


        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://localhost:7777/portalserver/portals/dashboard/pages/index");

        //request.addHeader("Set-Cookie", "BBTracking=\"Mw==\";Version=1;Path=/;Expires=Wed, 23-Dec-2015 16:32:29 GMT;Max-Age=31536000");
        request.addHeader("Set-Cookie", "BBTracking=Mw==;Version=1;Path=/;Expires=Wed, 23-Dec-2015 16:32:29 GMT;Max-Age=31536000");

        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the response
        BufferedReader rd = null;
        try {
            rd = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = "";
        StringBuilder textView = new StringBuilder();
        try {
            while ((line = rd.readLine()) != null) {
                textView.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(textView.toString());
        System.out.println(response.getStatusLine());
    }
}
