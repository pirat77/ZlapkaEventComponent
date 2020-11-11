package com.codecool.zlapka.eventcomponent.model;

import javax.persistence.*;

@Entity(name = "ticket")
public class Ticket extends ZlapkaEntityModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
