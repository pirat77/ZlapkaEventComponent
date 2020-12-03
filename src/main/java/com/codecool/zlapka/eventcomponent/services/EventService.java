package com.codecool.zlapka.eventcomponent.services;

import com.codecool.zlapka.eventcomponent.model.Event;
import com.codecool.zlapka.eventcomponent.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

public class EventService {

    @Autowired
    private JsonMapper jsonMapper;
    private EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Optional<Event> add(String jsonElement) {
        Optional<Event> optional = jsonMapper.getObjectFromJson(jsonElement);
        if (optional.isEmpty()) return optional;
        // TODO: send bind request to localization service
        return Optional.of(repository.save(optional.get()));
    }

    public String getById(long id){
        return jsonMapper.jsonRepresentation(repository.findById(id));
    }

    public long replace(String string){
        Optional<Event> newEvent = jsonMapper.getObjectFromJson(string);
        newEvent.ifPresent(event -> delete(Long.valueOf(event.getId())));
        if (repository.save(newEvent.get()).equals(newEvent.get())) return 1;
        return -1;
    }

    public long delete(Long id){
        repository.deleteById(id);
        return 1;
    }

//    public String getByCategory(Category category){
//        return jsonMapper.jsonRepresentation(repository.findByCategory(category.getName()));
//    }

//    public String getAll(){
//        return jsonMapper.jsonRepresentation(repository.findAll());
//    }
}