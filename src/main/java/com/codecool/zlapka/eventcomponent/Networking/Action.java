package com.codecool.zlapka.eventcomponent.Networking;

public enum Action {
    POST("POST", "add"),
    PUT("PUT", "update"),
    DELETE("DELETE", "delete");

    private String method;
    private String action;

    Action(String method, String action){
        this.method = method;
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public String getAction() {
        return action;
    }
}
