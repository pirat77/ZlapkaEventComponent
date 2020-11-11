package com.codecool.zlapka.eventcomponent.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JsonMapper {

    @Autowired
    private ObjectMapper mapper;

    public String jsonRepresentation(Object object) {
        String jsonOutput = "";
        try {
            jsonOutput = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            jsonOutput = "{}";
        }
        return jsonOutput;
    }

    public String jsonRepresentation(List<Object> objectList){
        if (objectList.isEmpty()) return "{}";
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("{");
        outputBuilder.append(objectList.get(0).getClass().getName());
        outputBuilder.append("s: [");
        for (Object object: objectList){
            outputBuilder.append(jsonRepresentation(object));
            outputBuilder.append(",");
        }
        outputBuilder.append("]}");
        return outputBuilder.toString();
    }

    public <T> Optional<T> getObjectFromJson(String json, Class<T> objectClass) {
        Optional<T> optionalInstance = Optional.empty();
        try {
            T newInstance = mapper.readValue(json, objectClass);
            optionalInstance = Optional.of(newInstance);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return optionalInstance;
    }

    public <T> List<T> getListOfObjectsFromJson(String jsonObjectList, Class<T> objectClass) {
        String[] path = objectClass.getName().split(".");
        String objectName = path[path.length -1];
        if (!jsonObjectList.startsWith(String.format("{\"%ss\":[", objectName))) return Collections.emptyList();
        String jsonObjectsInString = jsonObjectList.split("\\[", 1)[1].split("]")[0];
        String[] jsonObjects = jsonObjectsInString.split(",");
        if (jsonObjects[jsonObjects.length - 1].length() == 0) {
            jsonObjects = Arrays.copyOfRange(jsonObjects, 0, jsonObjects.length - 1);
        }
        List<T> resultObjects = new ArrayList<>();
        for (String object : jsonObjects) {
            getObjectFromJson(object, objectClass).ifPresent(resultObjects::add);
        }
        return resultObjects;
    }


}
