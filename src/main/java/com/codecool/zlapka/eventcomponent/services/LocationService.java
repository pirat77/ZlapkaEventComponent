package com.codecool.zlapka.eventcomponent.services;

import com.codecool.zlapka.eventcomponent.model.Event;
import com.codecool.zlapka.eventcomponent.model.Location;
import com.codecool.zlapka.eventcomponent.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class LocationService {

    @Autowired
    private JsonMapper jsonMapper;
    private LocationRepository repository;

    public LocationService(LocationRepository locationRepository){
        this.repository = locationRepository;
    }

    public long delete(Long id) {
            // TODO: send unbind request to localization service
            repository.deleteById(id);
            return 1;
    }

    public long replace(String location) {
        Optional<Location> newLocation = jsonMapper.getLocationFromJson(location);
        newLocation.ifPresent(event -> delete(Long.valueOf(event.getId())));
        if (repository.save(newLocation.get()).equals(newLocation.get())) return 1;
        return -1;
    }

    public Optional<Location> add(String jsonObject) {
        Optional<Location> optional = jsonMapper.getLocationFromJson(jsonObject);
        if (optional.isEmpty()) return optional;
        return Optional.of(repository.save(optional.get()));
    }
}
