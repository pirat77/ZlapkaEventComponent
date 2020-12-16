package com.codecool.zlapka.eventcomponent.model;

public enum EventStatus {
    PRIVATE("private"),
    CANCELLED("cancelled"),
    OPEN("open"),
    RESCHEDULED("rescheduled");


    private final String name;

    EventStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
