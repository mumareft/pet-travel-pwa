package com.pettravel.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.pettravel.models.Pet;

public class TaskResponse {

    private Long id;
    private String ruleName;
    private LocalDateTime dueDate;
    private Pet pets;
    private boolean status;

    public TaskResponse(Long id, String ruleName, LocalDateTime dueDate, Pet pet, boolean status) {
        this.id = id;
        this.ruleName = ruleName;
        this.dueDate = dueDate;
        this.pets = pet;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Pet getPets() {
        return pets;
    }

    public boolean isCompleted() {
        return status;
    }
}
