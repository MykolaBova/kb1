package com.mb.http;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Created by m.bova on 12/23/2014.
 * Get Cookie value and set cookie value
 * http://www.java2s.com/Code/Java/Apache-Common/GetCookievalueandsetcookievalue.htm
 */
public class GetCookiePrintAndSetValue {

    public static void main(String args[]) throws Exception {

        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "My Browser");
        GetMethod method = new GetMethod("http://localhost:7777/portalserver/portals/dashboard/pages/index");

        method.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
        //method.addRequestHeader("Cookie", "abc=11");
        method.setRequestHeader("Cookie", "special-cookie=value");

        try{

            int code1 = client.executeMethod(method);

            Cookie[] cookies = client.getState().getCookies();
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                System.err.println(
                        "Cookie: " + cookie.getName() +
                                ", Value: " + cookie.getValue() +
                                ", IsPersistent?: " + cookie.isPersistent() +
                                ", Expiry Date: " + cookie.getExpiryDate() +
                                ", Domain: " + cookie.getDomain() +
                                ", path: " + cookie.getPath() +
                                ", Comment: " + cookie.getComment());

                ///cookie.setValue("My own value");
                if(i == 0) {
                    cookie.setName("BBTracking");
                    //
                    cookie.setValue("Mw==");
                    //cookie.setValue("\"Mw==\"");
                }
            }

            System.err.println("code1="+code1);


            Cookie cookie1 = new Cookie();
            cookie1.setName("BBTracking");
            cookie1.setValue("Mw==");
            cookie1.setDomain("localhost");
            cookie1.setPath("/");
            client.getState().addCookie(cookie1);


            int code2 = client.executeMethod(method);
            System.err.println("-----------------------------------------------------------");
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                System.err.println(
                        "Cookie: " + cookie.getName() +
                                ", Value: " + cookie.getValue() +
                                ", IsPersistent?: " + cookie.isPersistent() +
                                ", Expiry Date: " + cookie.getExpiryDate() +
                                ", Domain: " + cookie.getDomain() +
                                ", path: " + cookie.getPath() +
                                ", Comment: " + cookie.getComment());

            }

            System.err.println("code2="+code2);

        } catch(Exception e) {
            System.err.println(e);
        } finally {
            method.releaseConnection();
        }
    }

}