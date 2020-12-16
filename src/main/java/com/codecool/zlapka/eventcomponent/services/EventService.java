package com.codecool.zlapka.eventcomponent.services;

import com.codecool.zlapka.eventcomponent.Networking.ConnectionProvider;
import com.codecool.zlapka.eventcomponent.Networking.EventBond;
import com.codecool.zlapka.eventcomponent.Networking.OwnerBond;
import com.codecool.zlapka.eventcomponent.model.Category;
import com.codecool.zlapka.eventcomponent.model.Event;
import com.codecool.zlapka.eventcomponent.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EventService {

    @Autowired
    private JsonMapper jsonMapper;
    @Autowired
    private ConnectionProvider connectionProvider;
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Optional<Event> add(String jsonElement) {
        Optional<Event> optional = jsonMapper.getEventFromJson(jsonElement);
        if (optional.isEmpty()) return optional;
        EventBond eventBond = new EventBond(optional.get().getLocationId(),
                                            optional.get().getIdString());
        OwnerBond ownerBond = new OwnerBond(optional.get().getIdString(),
                                            optional.get().getOwnerId(),
                                            optional.get().getName());
        System.out.println(eventBond.toJson());
        System.out.println(ownerBond.toJson());
        if (bindToLocalization(eventBond) && bindToOwner(ownerBond)){
            System.out.println("Bond done!");
            return Optional.of(eventRepository.save(optional.get()));
        }
        System.out.println("bond failled!");
        return Optional.ofNullable(null);
        // TODO: clean this
    }

    public boolean bindToOwner(OwnerBond ownerBond){
        try {
            HttpURLConnection connection = connectionProvider.ownerBondConnection(ownerBond.getOwnerId(), "add");
            byte[] requestBody = ownerBond.toJson().getBytes(StandardCharsets.UTF_8);
            if (connectionProvider.postRequest(requestBody, connection)) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean bindToLocalization(EventBond eventBond) {
        try {
            HttpURLConnection connection = connectionProvider.localizationBondConnection();
            byte[] requestBody = eventBond.toJson().getBytes(StandardCharsets.UTF_8);
            if (connectionProvider.postRequest(requestBody, connection)) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getByStringId(String stringId){
        return jsonMapper.jsonRepresentation(eventRepository.findByStringId(stringId));
    }

    public String getEventsByLocationId(String locationId) {
        return jsonMapper.jsonRepresentation(eventRepository.findEventsByLocationId(locationId));
    }

    public long replace(String string){
        Optional<Event> newEvent = jsonMapper.getEventFromJson(string);
        newEvent.ifPresent(event -> delete(event.getId()));
        if (eventRepository.save(newEvent.get()).equals(newEvent.get())) return 1;
        return -1;
    }

    public long delete(Long id){
        // TODO: send unbind request to localization service
        eventRepository.deleteById(id);
        return 1;
    }

    public String getByCategory(Category category){
        return jsonMapper.jsonRepresentation(eventRepository.findByCategory(category.getName()));
    }

    public String getAll(){
        return jsonMapper.jsonRepresentation(eventRepository.findAll());
    }
}
