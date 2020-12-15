package com.codecool.zlapka.eventcomponent.Networking;


import com.codecool.zlapka.eventcomponent.services.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class EventBond {
//    @Autowired
//    private JsonMapper jsonMapper;
// TODO: automate mapper
    private String localizationId;
    private String eventId;

    public EventBond(String localizationId, String eventId){
        this.localizationId = localizationId;
        this.eventId = eventId;
    }

    public String toJson(){
        return "{\"eventId\":\"" + eventId + "\", \"localizationId\":\"" + localizationId + "\"}";

        //return jsonMapper.jsonRepresentation(this);
    }
}
