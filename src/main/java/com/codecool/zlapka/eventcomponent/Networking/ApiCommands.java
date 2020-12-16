package com.codecool.zlapka.eventcomponent.Networking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

@Service
public class ApiCommands {

    @Autowired
    ConnectionProvider connectionProvider;

    public boolean bindToLocalization(EventBond eventBond, Action action) {
        try {
            HttpURLConnection connection = connectionProvider.localizationBondConnection();
            byte[] requestBody = eventBond.toJson().getBytes(StandardCharsets.UTF_8);
            if (connectionProvider.sendRequest(requestBody, connection, action.getMethod())) return true;
        } catch (IOException e) {
            System.out.println("Localization bond failed");
        }
        return false;
    }

    public boolean bindToOwner(OwnerBond ownerBond, Action action){
        try {
            HttpURLConnection connection = connectionProvider.ownerBondConnection(ownerBond.getOwnerId(), action.getAction());
            byte[] requestBody = ownerBond.toJson().getBytes(StandardCharsets.UTF_8);
            if (connectionProvider.sendRequest(requestBody, connection, action.getMethod())) return true;
        } catch (IOException e) {
            System.out.println("Owner bond failed");
        }
        return false;
    }
}
