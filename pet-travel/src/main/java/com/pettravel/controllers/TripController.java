package com.pettravel.controllers;

import com.pettravel.dto.DeadlineResponse;
import com.pettravel.models.Trip;
import com.pettravel.repositories.TripRepository;
import com.pettravel.services.TravelRuleService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping ("/trips")
public class TripController {
    
    private final TravelRuleService travelRuleService;
    private final TripRepository tripRepository;

    public TripController(TripRepository tripRepository, TravelRuleService travelRuleService) {
        this.tripRepository = tripRepository;
        this.travelRuleService = travelRuleService;
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
    public Trip createTrip(@RequestBody Trip trip) {
        return tripRepository.save(trip);
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

        Trip trip = tripRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Trip not found"));
        
        return trip.getTravelRules().stream()
                .map(rule -> {
                    LocalDate deadline = travelRuleService.calculateDeadline(trip, rule);
                    return new DeadlineResponse(rule.getName(), deadline);
                })
                .toList();
    }
}