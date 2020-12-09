package com.codecool.zlapka.eventcomponent.services;

import com.codecool.zlapka.eventcomponent.model.Category;
import com.codecool.zlapka.eventcomponent.model.Event;
import com.codecool.zlapka.eventcomponent.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

public class EventService {

    @Autowired
    private JsonMapper jsonMapper;
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Optional<Event> add(String jsonElement) {
        Optional<Event> optional = jsonMapper.getEventFromJson(jsonElement);
        if (optional.isEmpty()) return optional;
        // TODO: send bind request to localization service
        return Optional.of(eventRepository.save(optional.get()));
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
