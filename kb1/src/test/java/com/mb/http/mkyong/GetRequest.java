package com.mb.http.mkyong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Created by m.bova on 12/23/2014.
 * Apache HttpClient Examples
 * http://www.mkyong.com/java/apache-httpclient-examples/
 */
public class GetRequest {
    private static final String USER_AGENT = "mb";

    public static void main(String[] args) {

        String url = "http://www.google.com/search?q=httpClient";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);
    }
}