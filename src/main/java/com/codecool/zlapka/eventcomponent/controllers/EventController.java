package com.codecool.zlapka.eventcomponent.controllers;

import com.codecool.zlapka.eventcomponent.Networking.EventBond;
import com.codecool.zlapka.eventcomponent.Security.UUIDprovider;
import com.codecool.zlapka.eventcomponent.services.EventService;
import com.codecool.zlapka.eventcomponent.services.EventStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    private final String path = "/event";
    private final String eventBondPath = "/event/bond";
    private final String userPath = "/event/bond/user";

    @Autowired
    private EventService eventService;
    @Autowired
    private EventStatusService eventStatusService;

    @GetMapping(value = path)
    public String getEvent(@RequestParam(required = false) String idString,
                           @RequestParam(required = false) String userId,
                           @RequestParam(required = false) String locationId) {
        String event = eventService.getByStringId(idString);
        if (event.equals("{}")) return eventStatusService.statusAfterGetElementsNotFound(path);
        return event;
    }

    @PostMapping(value = eventBondPath)
    public String bondEvent(){
        eventService.bindToLocalization(new EventBond("3e279271-f02e-48be-9309-277e6f51f987",
                UUIDprovider.generateType1UUID().toString()
        ));
        //TODO: handle edge cases
        return "done";
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

    @PutMapping(value = userPath)
    public String putUser(@RequestBody String userToEvent){
        // TODO: implements this :P
        return "do this";
    }

}
