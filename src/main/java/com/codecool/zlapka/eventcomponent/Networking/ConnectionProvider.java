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
    private String userPATH = "http://5.135.20.171:8080/userAPI/users/";

    public ConnectionProvider() throws IOException { }

    public HttpURLConnection ownerBondConnection(String UUID, String action) throws IOException {
        URL userURL = new URL(userPATH + UUID + "/events/" + action);
        return (HttpURLConnection) userURL.openConnection();
    }

    public HttpURLConnection localizationBondConnection() throws IOException {
        return (HttpURLConnection) locationBondURL.openConnection();
    }

    public boolean postRequest(byte[] requestBody, HttpURLConnection connection){
        try {
            connection.setRequestMethod("POST");
            System.out.println(connection.getRequestMethod()); // test log
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(requestBody.length));
            connection.setConnectTimeout(10000);
            connection.setDoOutput(true);
            System.out.println(connection.toString()); //test log
            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
            System.out.println(writer.toString()); //test log
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
            e.printStackTrace();
        }
        return false;
    }
}
