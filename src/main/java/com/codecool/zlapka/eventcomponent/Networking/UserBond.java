package com.codecool.zlapka.eventcomponent.Networking;

import com.codecool.zlapka.eventcomponent.services.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserBond {
    @Autowired
    private JsonMapper jsonMapper;
    private String eventId;
    private String userId;

    public UserBond(String eventId, String userId){
        this.userId = userId;
        this.eventId = eventId;
    }

    public UserBond(String json){

    }
    public String toJson(){
        return jsonMapper.jsonRepresentation(this);
    }
}
