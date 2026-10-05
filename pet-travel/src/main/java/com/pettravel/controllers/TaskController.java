package com.pettravel.controllers;

import org.springframework.web.bind.annotation.*;

import com.pettravel.dto.TaskResponse;
import com.pettravel.services.TripTaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TripTaskService tripTaskService;

    public TaskController(TripTaskService tripTaskService) {
        this.tripTaskService = tripTaskService;
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return tripTaskService.getTaskById(id);
    }

    @PutMapping("/{id}/status")
    public TaskResponse updateTaskStatus(
            @PathVariable Long id,
            @RequestParam boolean isCompleted) {

        return tripTaskService.updateTaskStatus(id, isCompleted);
    }
}