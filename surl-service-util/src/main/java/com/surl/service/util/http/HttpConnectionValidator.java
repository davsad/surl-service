package com.surl.service.util.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.surl.service.util.exception.InvalidUrlException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class HttpConnectionValidator {
    
    private static final String HTTPS = "https://";
    
    private HttpConnectionValidator() {
        //Restrict instantiating helper for now
    }

    public static boolean isValidUrl(final String url) throws InvalidUrlException {
        boolean valid;
        try {
            final URL fUrl = new URL(url);
            if (url.startsWith(HTTPS)) {
                HttpsURLConnection con = getHttpsConnection(fUrl);
                valid = con.getResponseCode() == HttpURLConnection.HTTP_OK;
                con.disconnect();
            } else {
                HttpURLConnection con = getHttpConnection(fUrl);
                valid = con.getResponseCode() == HttpURLConnection.HTTP_OK;
                con.disconnect();
            }
        } catch (Exception e) {
            log.debug("Failed to validate url {}, exception caught {}", url, e.getLocalizedMessage());
            throw new InvalidUrlException(url);
        }
        return valid;
    }
    
    public static void httpGet(final String url) throws IOException {
        final URL fUrl = new URL(url);
        final HttpURLConnection con = getHttpConnection(fUrl);
        con.connect();
        log.info("HTTP GET Response for url {} Status {}", url, con.getResponseCode());
        con.disconnect();
    }
    
    private static HttpURLConnection getHttpConnection(final URL url) throws IOException {
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setInstanceFollowRedirects(false);
        return con;
    }
    
    private static HttpsURLConnection getHttpsConnection(final URL url) throws IOException {
        final HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setInstanceFollowRedirects(false);
        return con;
    }
}
