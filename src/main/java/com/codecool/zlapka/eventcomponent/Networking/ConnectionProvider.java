package com.codecool.zlapka.eventcomponent.Networking;

import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@Service
public class ConnectionProvider {
    private final URL googleURL = new URL("http://google.com");
    private final URL locationBondURL = new URL("http://5.135.20.171:34225/localization/bond/event");
    private final URL userURL = new URL("http://5.135.20.171:8081/user");

    public ConnectionProvider() throws IOException { }

    public HttpURLConnection localizationBondConnection() throws IOException {
        return (HttpURLConnection) locationBondURL.openConnection();
    }
}
