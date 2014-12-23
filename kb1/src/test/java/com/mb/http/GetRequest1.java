package com.mb.http;

import java.io.IOException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by m.bova on 12/23/2014.
 * Apache HttpClient - Tutorial
 * http://www.vogella.com/tutorials/ApacheHttpClient/article.html
 */
public class GetRequest1 {
    public static void main(String[] args) {


        //HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://localhost:7777/portalserver/portals/dashboard/pages/index");
        request.addHeader("User-Agent", "Mr");

        request.addHeader("Content-Type", "application/xml");
        request.addHeader("application/xml", "UTF-8");

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("localhost", 7777),
                new UsernamePasswordCredentials("admin", "admin"));

        //HttpResponse response = null;

        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
            HttpGet httpget = new HttpGet("http://localhost/");

            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httpget);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
/*
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
*/
    }
}
