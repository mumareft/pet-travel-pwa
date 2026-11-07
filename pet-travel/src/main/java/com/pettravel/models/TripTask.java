package com.pettravel.models;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "trip_tasks")
public class TripTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Trip trip;

    @ManyToOne
    private Pet pet;

    @ManyToOne
    private Rule rule;

    private LocalDateTime dueDate;

    private boolean completed;

    @Enumerated(EnumType.STRING)
    private JourneyType journeyType;

    public enum JourneyType {
        OUTBOUND,
        RETURN
    }

    public TripTask() {
    }

    public TripTask(Trip trip, Pet pet, Rule rule, LocalDateTime dueDate, boolean completed, JourneyType journeyType) {
        this.trip = trip;
        this.pet = pet;
        this.rule = rule;
        this.dueDate = dueDate;
        this.completed = completed;
        this.journeyType = journeyType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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
