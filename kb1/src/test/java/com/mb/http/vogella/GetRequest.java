package com.mb.http.vogella;

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
public class GetRequest {
    public static void main(String[] args) {


        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://www.vogella.com");
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
    }
}
