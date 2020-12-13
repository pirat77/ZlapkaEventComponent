package com.codecool.zlapka.eventcomponent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "event")
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_string")
    private String idString;
    private String name;
    private String description;
    @Column(name = "max_participant")
    private int maxParticipant;
    private Date date;
    private Time time;
    private int duration;
    @Column(name = "public_event")
    private boolean publicEvent;
    private boolean archived;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_id")
    private Category category;
    @Column(name = "location_id")
    private String locationId;
    @Column(name = "organization_id")
    private String organizationId;
    @Column(name = "owner_id")
    private String ownerId;
    @ElementCollection
    private List<String> users;

    public Event() {
        super();
    }

    public Event(long id, String idString, String name, String description, int maxParticipant, Date date, Time time, int duration, boolean publicEvent, boolean archived, Category category, String locationId, String organizationId, String ownerId, List<String> users) {
        this.id = id;
        this.idString = idString;
        this.name = name;
        this.description = description;
        this.maxParticipant = maxParticipant;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.publicEvent = publicEvent;
        this.archived = archived;
        this.category = category;
        this.locationId = locationId;
        this.organizationId = organizationId;
        this.ownerId = ownerId;
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxParticipant() {
        return maxParticipant;
    }

    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isPublicEvent() {
        return publicEvent;
    }

    public void setPublicEvent(boolean publicEvent) {
        this.publicEvent = publicEvent;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}