package com.codecool.zlapka.eventcomponent.model;

public enum Category {
    FPS("FPS"),
    RPG("RPG"),
    RTS("RTS"),
    TPP("TPP"),
    ARCADE("ARCADE"),
    RACER("RACER"),
    SPORT("SPORT"),
    FIGHT("FIGHT"),
    ADVENTURE("ADVENTURE"),
    LOGIC("LOGIC");

    private final String name;

    private Category(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
