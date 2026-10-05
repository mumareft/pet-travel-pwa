package com.pettravel.controllers;

import com.pettravel.dto.TaskResponse;
import com.pettravel.models.Trip;
import com.pettravel.services.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @GetMapping ("/{id}/tasks")
    public List<TaskResponse> getTripTasks(@PathVariable Long id) {
        return tripService.getTripTasks(id);
    }

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }

    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable Long id, @RequestBody Trip updatedTrip) {
        return tripService.updateTrip(id, updatedTrip);
    }

    @PutMapping ("/{tripId}/pets/{petId}")
    public Trip addPetToTrip(@PathVariable Long tripId, @PathVariable Long petId) {
        return tripService.addPetToTrip(tripId, petId);
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }
}