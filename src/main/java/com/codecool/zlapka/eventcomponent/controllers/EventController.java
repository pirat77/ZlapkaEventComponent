package com.codecool.zlapka.eventcomponent.controllers;

import com.codecool.zlapka.eventcomponent.services.EventService;
import com.codecool.zlapka.eventcomponent.services.EventStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    private final String path = "/event";

    @Autowired
    private EventService eventService;
    @Autowired
    private EventStatusService eventStatusService;

    @GetMapping(value = path)
    public String getEvent(@RequestParam(required = false) String idString) {
        String event = eventService.getByStringId(idString);
        if (event.equals("{}")) return eventStatusService.statusAfterGetElementsNotFound(path);
        return event;
    }

    @DeleteMapping(value = path)
    public String deleteEvent(@RequestParam(required = true) Long id){
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
}
