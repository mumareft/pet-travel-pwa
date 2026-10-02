package com.pettravel.controllers;

import com.pettravel.dto.DeadlineResponse;
import com.pettravel.models.Trip;
import com.pettravel.repositories.TripRepository;
import com.pettravel.services.TripTaskService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/trips")
public class TripController {
    
    private final TripTaskService tripTaskService;
    private final TripRepository tripRepository;

    public TripController(TripRepository tripRepository, TripTaskService tripTaskService) {
        this.tripRepository = tripRepository;
        this.tripTaskService = tripTaskService;
    }

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @PostMapping
    @Transactional
    public Trip createTrip(@RequestBody Trip trip) {
        Trip savedTrip = tripRepository.save(trip);
        // Generate and persist one task for each rule matching the destination country.
        tripTaskService.createTasksForTrip(savedTrip);
        return savedTrip;
    }

    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable Long id, @RequestBody Trip updatedTrip) {
        return tripRepository.findById(id)
                .map(trip -> {
                    trip.setDestinationCountry(updatedTrip.getDestinationCountry());
                    trip.setDepartureDate(updatedTrip.getDepartureDate());
                    trip.setReturnDate(updatedTrip.getReturnDate());
                    return tripRepository.save(trip);
                })
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripRepository.deleteById(id);
    }


    @GetMapping ("/{id}/deadlines")
    public List<DeadlineResponse> getTripDeadlines(@PathVariable Long id) {
        tripRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Trip not found"));
        return tripTaskService.getTasksForTrip(id).stream()
                .map(task -> new DeadlineResponse(
                        task.getRule().getName(),
                        task.getEarliestDateTime(),
                        task.getLatestDateTime()))
                .toList();
    }
}