package com.codecool.zlapka.eventcomponent.model;

import javax.persistence.*;

@Entity(name = "organization")
public class Organization {

    @Id
    @Column(name ="organization_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
