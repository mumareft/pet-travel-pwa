package com.pettravel.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pettravel.dto.DeadlineResponse;
import com.pettravel.models.Trip;
import com.pettravel.repositories.TripRepository;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TripTaskService tripTaskService;

    public TripService(TripRepository tripRepository, TripTaskService tripTaskService) {
        this.tripRepository = tripRepository;
        this.tripTaskService = tripTaskService;
    }

    @Transactional(readOnly = true)
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Transactional
    public Trip createTrip(Trip trip) {
        Trip savedTrip = tripRepository.save(trip);
        // Coordinate trip persistence with generating and saving its rule-based tasks.
        tripTaskService.createTasksForTrip(savedTrip);
        return savedTrip;
    }

    @Transactional
    public Trip updateTrip(Long id, Trip updatedTrip) {
        return tripRepository.findById(id)
                .map(trip -> {
                    trip.setOriginCountry(updatedTrip.getOriginCountry());
                    trip.setDestinationCountry(updatedTrip.getDestinationCountry());
                    trip.setDepartureDate(updatedTrip.getDepartureDate());
                    trip.setArrivalDate(updatedTrip.getArrivalDate());
                    trip.setReturnDate(updatedTrip.getReturnDate());
                    Trip savedTrip = tripRepository.save(trip);
                    // Replace saved deadlines so every task matches the updated trip.
                    tripTaskService.recalculateTasksForTrip(savedTrip);
                    return savedTrip;
                })
                .orElse(null);
    }

    @Transactional
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<DeadlineResponse> getTripDeadlines(Long id) {
        tripRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Trip not found"));
        return tripTaskService.getTasksForTrip(id).stream()
                .map(task -> new DeadlineResponse(
                        task.getRule().getName(),
                        task.getEarliestDateTime(),
                        task.getLatestDateTime()))
                .toList();
    }
}
