package com.codecool.zlapka.eventcomponent.Networking;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class ConnectionProvider {
    private final URL googleURL = new URL("http://google.com");
    private final URL locationBondURL = new URL("http://5.135.20.171:34225/localization/bond/event");
    private String userPATH = "http://5.135.20.171:44387/userapi/users/";

    public ConnectionProvider() throws IOException { }

    public HttpURLConnection ownerBondConnection(String UUID, String action) throws IOException {
        URL userURL = new URL(userPATH + "userId/events/" + action + "?userid=" + UUID );
        return (HttpURLConnection) userURL.openConnection();
    }

    public HttpURLConnection localizationBondConnection() throws IOException {
        return (HttpURLConnection) locationBondURL.openConnection();
    }

    public boolean sendRequest(byte[] requestBody, HttpURLConnection connection, String requestMethod){
        try {
            connection.setRequestMethod(requestMethod);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(requestBody.length));
            connection.setConnectTimeout(10000);
            connection.setDoOutput(true);
            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
            writer.write(requestBody);
            writer.flush();
            writer.close();
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            System.out.println(content.toString());
            connection.disconnect();
            return true;
        } catch (IOException e) {
            System.out.println("Request failed.");;
        }
        return false;
    }
}
