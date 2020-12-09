package com.codecool.zlapka.eventcomponent.controllers;

import com.codecool.zlapka.eventcomponent.services.EventService;
import com.codecool.zlapka.eventcomponent.services.LocationService;
import com.codecool.zlapka.eventcomponent.services.LocationStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventLocalController {

    private final String path = "/event/localization";

    @Autowired
    private LocationService locationService;
    @Autowired
    private EventService eventService;
    @Autowired
    private LocationStatusService locationStatusService;

    @GetMapping(value = path)
    public String getLocation(@RequestParam(required = false) String locationId) {
        String event = eventService.getEventsByLocationId(locationId);
        if (event.equals("{}")) return locationStatusService.statusAfterGetElementsNotFound(path);
        return event;
    }

    @PutMapping(value = path)
    public String putEvent(@RequestBody String event){
        return locationStatusService.statusAfterReplace(path, locationService.replace(event));
    }

    @PostMapping(value = path)
    public String postEvent(@RequestBody String jsonObject){
        return locationStatusService.statusAfterAdd(path, locationService.add(jsonObject));
    }
}
