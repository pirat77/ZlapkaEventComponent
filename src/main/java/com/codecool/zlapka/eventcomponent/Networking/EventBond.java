package com.codecool.zlapka.eventcomponent.Networking;

public class EventBond {

    private String localizationId;
    private String eventId;

    public EventBond(String localizationId, String eventId){
        this.localizationId = localizationId;
        this.eventId = eventId;
    }

    public String toJson(){
        return "{\"eventId\":\"" + eventId + "\", \"localizationId\":\"" + localizationId + "\"}";
    }
}
