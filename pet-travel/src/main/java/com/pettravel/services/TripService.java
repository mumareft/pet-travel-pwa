package com.pettravel.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pettravel.dto.TaskResponse;
import com.pettravel.models.Pet;
import com.pettravel.models.Trip;
import com.pettravel.repositories.TripRepository;
import com.pettravel.repositories.PetRepository;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TripTaskService tripTaskService;
    private final PetRepository petRepository;

    public TripService(TripRepository tripRepository, TripTaskService tripTaskService, PetRepository petRepository) {
        this.tripRepository = tripRepository;
        this.tripTaskService = tripTaskService;
        this.petRepository = petRepository;
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

                trip.setOriginDepartureDT(updatedTrip.getOriginDepartureDT());
                trip.setOutboundArrivalDT(updatedTrip.getOutboundArrivalDT());
                trip.setReturnDepartureDT(updatedTrip.getReturnDepartureDT());
                trip.setReturnArrivalDT(updatedTrip.getReturnArrivalDT());

                trip.setPets(updatedTrip.getPets());

                Trip savedTrip = tripRepository.save(trip);

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
    public List<TaskResponse> getTripTasks(Long id) {
        return tripTaskService.getTasksForTrip(id).stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getRule().getName(),
                        task.getEarliestDateTime(),
                        task.getLatestDateTime(),
                        task.getPet(),
                        task.isCompleted()
                        ))
                .toList();
    }

    @Transactional
    public Trip addPetToTrip(Long tripId, Long petId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        if (!trip.getPets().contains(pet)) {
            trip.getPets().add(pet);
        }
        return tripRepository.save(trip);
    }
}
