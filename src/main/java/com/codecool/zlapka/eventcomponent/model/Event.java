package com.codecool.zlapka.eventcomponent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "event")
public class Event extends ZlapkaEntityModel {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "max_participant")
    int maxParticipant;
    private Date date;
    private Time time;
    private int duration;
    @Column(name = "public_event")
    private boolean publicEvent;
    private boolean archived;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="location_id", referencedColumnName = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name="organization_id", referencedColumnName = "organization_id")
    private Organization organization;

    @OneToMany
    private List<Ticket> tickets;

    @ManyToMany
    private List<User> userList;

    public Event(){
        super();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Event(Long id, String name, String description, int maxParticipant, Date date, Time time, int duration, boolean publicEvent, boolean archived, Category category, Location location, Organization organization, List<Ticket> tickets, List<User> userList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxParticipant = maxParticipant;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.publicEvent = publicEvent;
        this.archived = archived;
        this.category = category;
        this.location = location;
        this.organization = organization;
        this.tickets = tickets;
        this.userList = userList;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
