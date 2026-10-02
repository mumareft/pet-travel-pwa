package com.pettravel.models;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TripTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime earliestDateTime;
    private ZonedDateTime latestDateTime;

    @ManyToOne(optional = false)
    private Rule rule;

    private boolean status;

    @ManyToOne
    private Pet pet;

    @ManyToOne(optional = false)
    private Trip trip;

    public TripTask() {
    }

    public TripTask(ZonedDateTime earliestDateTime, ZonedDateTime latestDateTime, Rule rule, boolean status, Pet pet,
            Trip trip) {
        this.earliestDateTime = earliestDateTime;
        this.latestDateTime = latestDateTime;
        this.rule = rule;
        this.status = status;
        this.pet = pet;
        this.trip = trip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getEarliestDateTime() {
        return earliestDateTime;
    }

    public void setEarliestDateTime(ZonedDateTime earliestDateTime) {
        this.earliestDateTime = earliestDateTime;
    }

    public ZonedDateTime getLatestDateTime() {
        return latestDateTime;
    }

    public void setLatestDateTime(ZonedDateTime latestDateTime) {
        this.latestDateTime = latestDateTime;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
