package com.mb.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

/**
 * Created by m.bova on 12/23/2014.
 * Apache HttpClient - Tutorial
 * http://www.vogella.com/tutorials/ApacheHttpClient/article.html
 */
public class GetRequest22 {
    public static void main(String[] args) {


        //HttpClient client = new DefaultHttpClient();
        DefaultHttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet("http://localhost:7777/portalserver/portals/dashboard/pages/index");

        //request.addHeader("Set-Cookie", "BBTracking=\"Mw==\";Version=1;Path=/;Expires=Wed, 23-Dec-2015 16:32:29 GMT;Max-Age=31536000");
        request.addHeader("Set-Cookie", "BBTracking=Mw==;Version=1;Path=/;Expires=Wed, 23-Dec-2015 16:32:29 GMT;Max-Age=31536000");

        // http://stackoverflow.com/questions/2014700/preemptive-basic-authentication-with-apache-httpclient-4
        // <<
        // Pre-emptive authentication to speed things up
        BasicHttpContext localContext = new BasicHttpContext();

        BasicScheme basicAuth = new BasicScheme();
        localContext.setAttribute("preemptive-auth", basicAuth);

        client.addRequestInterceptor(new PreemptiveAuthInterceptor(), 0);
        // >>


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

class PreemptiveAuthInterceptor implements HttpRequestInterceptor {

    public void process(final HttpRequest request, final HttpContext context)
            throws HttpException, IOException {
        AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);

        // If no auth scheme avaialble yet, try to initialize it
        // preemptively
        if (authState.getAuthScheme() == null) {
            AuthScheme authScheme = (AuthScheme) context.getAttribute("preemptive-auth");
            CredentialsProvider credsProvider =
                    (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);
            HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            if (authScheme != null) {
                Credentials creds = credsProvider.getCredentials(
                        new AuthScope(targetHost.getHostName(), targetHost.getPort()));
                if (creds == null) {
                    throw new HttpException("No credentials for preemptive authentication");
                }
                authState.setAuthScheme(authScheme);
                authState.setCredentials(creds);
            }
        }

    }
}
