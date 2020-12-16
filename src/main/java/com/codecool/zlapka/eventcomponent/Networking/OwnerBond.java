package com.codecool.zlapka.eventcomponent.Networking;

public class OwnerBond {

    private String eventId;
    private String ownerId;
    private String name;

    public OwnerBond(String eventId, String ownerId, String name){
        this.ownerId = ownerId;
        this.eventId = eventId;
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String toJson(){
        return "{\"id\":\"" + eventId + "\", \"name\":\"" + name + "\"}";
    }
}
