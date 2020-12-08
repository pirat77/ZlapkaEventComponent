package com.codecool.zlapka.eventcomponent.model;

import javax.persistence.*;

@Entity(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name="id_string")
    private String idString;
    private String name;

    public Organization(){
        super();
    }

    public Organization(Long id, String idString, String name) {
        this.id = id;
        this.idString = idString;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
