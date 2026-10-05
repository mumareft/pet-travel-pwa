package com.pettravel.dto;

import java.time.ZonedDateTime;

import com.pettravel.models.Pet;

public class TaskResponse {

    private Long id;
    private String ruleName;
    private ZonedDateTime earliestDateTime;
    private ZonedDateTime latestDateTime;
    private Pet pets;
    private boolean status;

    public TaskResponse(Long id, String ruleName, ZonedDateTime earliestDateTime, ZonedDateTime latestDateTime, Pet pet, boolean status) {
        this.id = id;
        this.ruleName = ruleName;
        this.earliestDateTime = earliestDateTime;
        this.latestDateTime = latestDateTime;
        this.pets = pet;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public ZonedDateTime getEarliestDateTime() {
        return earliestDateTime;
    }

    public ZonedDateTime getLatestDateTime() {
        return latestDateTime;
    }

    public Pet getPets() {
        return pets;
    }

    public boolean isCompleted() {
        return status;
    }
}
