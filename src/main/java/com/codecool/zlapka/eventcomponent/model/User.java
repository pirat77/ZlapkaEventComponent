package com.codecool.zlapka.eventcomponent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    private String nick;
    @Column(name="id_string")
    private String idString;

    User(long id, String nick, String idString){
        this.id=id;
        this.nick=nick;
        this.idString=idString;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getIdString() { return idString; }

    public void setIdString(String idString) { this.idString = idString; }
}
