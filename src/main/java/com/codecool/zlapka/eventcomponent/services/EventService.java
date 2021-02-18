package com.codecool.zlapka.eventcomponent.services;

import com.codecool.zlapka.eventcomponent.Networking.*;
import com.codecool.zlapka.eventcomponent.Security.UUIDprovider;
import com.codecool.zlapka.eventcomponent.model.Event;
import com.codecool.zlapka.eventcomponent.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

public class EventService {

    @Autowired
    private JsonMapper jsonMapper;
    @Autowired
    private ApiCommands ApiCommands;
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Optional<Event> add(String jsonElement) {
        Optional<Event> optional = jsonMapper.getEventFromJson(jsonElement);
        if (optional.isEmpty()) return optional;
        optional.get().setIdString(UUIDprovider.generateType1UUID().toString());
        if (menageBonds(optional.get(), Action.POST)){
            System.out.println("Bond done!");
            return Optional.of(eventRepository.save(optional.get()));
        }
        System.out.println("Bond failed!");
        return Optional.ofNullable(null);
    }

    private boolean menageBonds(Event event, Action action){
        EventBond eventBond = new EventBond(event.getLocationId(),
                                            event.getIdString());
        //OwnerBond ownerBond = new OwnerBond(event.getIdString(),
        //                                    event.getOwnerId(),
        //                                    event.getName());
        System.out.println(eventBond.toJson());
        //System.out.println(ownerBond.toJson());
        return ApiCommands.bindToLocalization(eventBond, action); // && ApiCommands.bindToOwner(ownerBond, action);
    }

    public String getByStringId(String UUID){
        return jsonMapper.jsonRepresentation(eventRepository.findById(UUID));
    }

    public boolean enrollToEvent(){
       return false;
    }

    public boolean optOutFromEvent(){
        return false;
    }

    public long replace(String string){
        Optional<Event> newEvent = jsonMapper.getEventFromJson(string);
        newEvent.ifPresent(event -> delete(event.getIdString()));
        if (eventRepository.save(newEvent.get()).equals(newEvent.get()) && menageBonds(newEvent.get(), Action.POST))
            return 1;
        return -1;
    }

    public long delete(String UUID){
        Optional<Event> oldEvent = eventRepository.findById(UUID);
        if (oldEvent.isEmpty()) return -1;
        eventRepository.deleteById(UUID);
        menageBonds(oldEvent.get(), Action.DELETE);
        return 1;
    }

    public String getAll(){
        return jsonMapper.jsonRepresentation(eventRepository.findAll());
    }
}
