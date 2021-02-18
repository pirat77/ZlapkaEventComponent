package com.codecool.zlapka.eventcomponent.controllers;

import com.codecool.zlapka.eventcomponent.services.EventService;
import com.codecool.zlapka.eventcomponent.services.EventStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    private final String path = "/";
    private final String userPath = "/bond/user";

    @Autowired
    private EventService eventService;
    @Autowired
    private EventStatusService eventStatusService;

    @GetMapping(value = path)
    public String getEvent(@RequestParam(required = false) String UUID) {
        if (UUID == null) return eventService.getAll();
        String event = eventService.getByStringId(UUID);
        if (event.equals("{}")) return eventService.getAll();
        return event;
    }

    @DeleteMapping(value = path)
    public String deleteEvent(@RequestParam(required = true) String id){
        return eventStatusService.statusAfterDelete(path, eventService.delete(id));
    }

    @PutMapping(value = path)
    public String putEvent(@RequestBody String event){
        return eventStatusService.statusAfterReplace(path, eventService.replace(event));
    }

    @PostMapping(value = path)
    public String postEvent(@RequestBody String event){
        return eventStatusService.statusAfterAdd(path, eventService.add(event));
    }

    @PutMapping(value = userPath)
    public String putUser(@RequestBody String userToEvent){
        // TODO: implements this :P
        return "do this";
    }

    @DeleteMapping(value = userPath)
    public String removeUser(@RequestBody String userFromEvent){
        //TODO: implement this;
        return "do this";
    }
}
