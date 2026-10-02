package com.pettravel.controllers;

import com.pettravel.dto.DeadlineResponse;
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

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }

    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable Long id, @RequestBody Trip updatedTrip) {
        return tripService.updateTrip(id, updatedTrip);
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }

    @GetMapping("/{id}/deadlines")
    public List<DeadlineResponse> getTripDeadlines(@PathVariable Long id) {
        return tripService.getTripDeadlines(id);
    }
}