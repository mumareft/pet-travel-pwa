package com.pettravel.controllers;

import org.springframework.web.bind.annotation.*;

import com.pettravel.services.TripTaskService;

@RestController
@RequestMapping ("/tasks")
public class TaskController {
    private final TripTaskService tripTaskService;

    public TaskController(TripTaskService tripTaskService) {
        this.tripTaskService = tripTaskService;
    }

    @PutMapping("/{id}/status")
    public void updateTaskStatus(@PathVariable Long id, @RequestParam boolean isCompleted) {
        tripTaskService.updateTaskStatus(id, isCompleted);
    }
}
