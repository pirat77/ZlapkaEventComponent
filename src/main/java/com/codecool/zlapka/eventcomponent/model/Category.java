package com.codecool.zlapka.eventcomponent.model;

public enum Category {
    DANCE("Dance"),
    SPORT("Sport"),
    ART("Art"),
    OUTDOOR("Outdoor"),
    EDUCATION("Education"),
    CASUAL("Casual"),
    BUSINESS("Business");

    private final String name;

    Category(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
