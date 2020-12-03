package com.codecool.zlapka.eventcomponent.controllers;

import com.codecool.zlapka.eventcomponent.services.EventService;
import com.codecool.zlapka.eventcomponent.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public abstract class EventController {

    private final String path = "/event";

    @Autowired
    private EventService service;
    @Autowired
    private StatusService statusService;

    @GetMapping(value = path)
    public String getEvent(@RequestParam(required = false) long id) {
        String event = service.getById(id);
        if (event.equals("{}")) return statusService.statusAfterGetElementsNotFound(path);
        return event;
    }

    @DeleteMapping(value = path)
    public String deleteEvent(@RequestParam(required = false) Long id){
        return statusService.statusAfterDelete(path, service.delete(id));
    }

    @PutMapping(value = path)
    public String putEvent(@RequestBody String event){
        return statusService.statusAfterReplace(path, service.replace(event));
    }

    @PostMapping(value = path)
    public String postEvent(@RequestBody String jsonObject){
        return statusService.statusAfterAdd(path, service.add(jsonObject));
    }
}
