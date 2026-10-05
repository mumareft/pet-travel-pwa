package com.pettravel.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pettravel.dto.TaskResponse;
import com.pettravel.models.Country;
import com.pettravel.models.Pet;
import com.pettravel.models.Trip;
import com.pettravel.models.User;
import com.pettravel.repositories.CountryRepository;
import com.pettravel.repositories.PetRepository;
import com.pettravel.repositories.TripRepository;
import com.pettravel.repositories.UserRepository;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TripTaskService tripTaskService;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;


    public TripService(
            TripRepository tripRepository,
            TripTaskService tripTaskService,
            PetRepository petRepository,
            UserRepository userRepository,
            CountryRepository countryRepository
    ) {
        this.tripRepository = tripRepository;
        this.tripTaskService = tripTaskService;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
    }


    @Transactional(readOnly = true)
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Trip getTripById(Long id) {
        return tripRepository.findById(id)
                .orElse(null);
    }


    @Transactional
    public Trip createTrip(Trip trip) {

        /*
         * USER
         */
        if (trip.getUser() == null || trip.getUser().getId() == null) {
            throw new IllegalArgumentException("User is required");
        }

        User user = userRepository.findById(trip.getUser().getId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found: " + trip.getUser().getId()
                        )
                );

        trip.setUser(user);


        /*
         * ORIGIN COUNTRY
         */
        if (trip.getOrigin() == null || trip.getOrigin().getId() == null) {
            throw new IllegalArgumentException("Origin country is required");
        }

        Country origin = countryRepository.findById(trip.getOrigin().getId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Origin country not found: "
                                        + trip.getOrigin().getId()
                        )
                );

        trip.setOrigin(origin);


        /*
         * DESTINATION COUNTRY
         */
        if (trip.getDestination() == null
                || trip.getDestination().getId() == null) {

            throw new IllegalArgumentException(
                    "Destination country is required"
            );
        }

        Country destination =
                countryRepository.findById(trip.getDestination().getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Destination country not found: "
                                                + trip.getDestination().getId()
                                )
                        );

        trip.setDestination(destination);


        /*
         * PETS
         */
        List<Pet> managedPets = new ArrayList<>();

        if (trip.getPets() != null) {

            for (Pet pet : trip.getPets()) {

                if (pet.getId() == null) {
                    throw new IllegalArgumentException(
                            "Every pet must have an id"
                    );
                }

                Pet managedPet = petRepository.findById(pet.getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Pet not found: " + pet.getId()
                                )
                        );

                managedPets.add(managedPet);
            }
        }

        trip.setPets(managedPets);


        /*
         * SAVE TRIP
         */
        Trip savedTrip = tripRepository.save(trip);


        /*
         * GENERATE TASKS FROM RULES
         */
        tripTaskService.createTasksForTrip(savedTrip);


        return savedTrip;
    }


    @Transactional
    public Trip updateTrip(Long id, Trip updatedTrip) {

        Trip trip = tripRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Trip not found: " + id
                        )
                );


        /*
         * USER
         */
        if (updatedTrip.getUser() != null
                && updatedTrip.getUser().getId() != null) {

            User user = userRepository
                    .findById(updatedTrip.getUser().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "User not found: "
                                            + updatedTrip.getUser().getId()
                            )
                    );

            trip.setUser(user);
        }


        /*
         * ORIGIN
         */
        if (updatedTrip.getOrigin() != null
                && updatedTrip.getOrigin().getId() != null) {

            Country origin = countryRepository
                    .findById(updatedTrip.getOrigin().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Origin country not found: "
                                            + updatedTrip.getOrigin().getId()
                            )
                    );

            trip.setOrigin(origin);
        }


        /*
         * DESTINATION
         */
        if (updatedTrip.getDestination() != null
                && updatedTrip.getDestination().getId() != null) {

            Country destination = countryRepository
                    .findById(updatedTrip.getDestination().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Destination country not found: "
                                            + updatedTrip
                                                    .getDestination()
                                                    .getId()
                            )
                    );

            trip.setDestination(destination);
        }


        /*
         * DATES
         */
        trip.setOriginDepartureDT(
                updatedTrip.getOriginDepartureDT()
        );

        trip.setOutboundArrivalDT(
                updatedTrip.getOutboundArrivalDT()
        );

        trip.setReturnDepartureDT(
                updatedTrip.getReturnDepartureDT()
        );

        trip.setReturnArrivalDT(
                updatedTrip.getReturnArrivalDT()
        );


        /*
         * PETS
         */
        if (updatedTrip.getPets() != null) {

            List<Pet> managedPets = new ArrayList<>();

            for (Pet pet : updatedTrip.getPets()) {

                if (pet.getId() == null) {
                    throw new IllegalArgumentException(
                            "Every pet must have an id"
                    );
                }

                Pet managedPet = petRepository
                        .findById(pet.getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Pet not found: "
                                                + pet.getId()
                                )
                        );

                managedPets.add(managedPet);
            }

            trip.setPets(managedPets);
        }


        Trip savedTrip = tripRepository.save(trip);


        /*
         * Recalculate tasks because countries, pets,
         * or dates may have changed.
         */
        tripTaskService.recalculateTasksForTrip(savedTrip);


        return savedTrip;
    }


    @Transactional
    public void deleteTrip(Long id) {

        if (!tripRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Trip not found: " + id
            );
        }

        tripRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public List<TaskResponse> getTripTasks(Long id) {

        return tripTaskService
                .getTasksForTrip(id)
                .stream()
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
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Trip not found: " + tripId
                        )
                );

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Pet not found: " + petId
                        )
                );

        boolean alreadyAdded = trip.getPets()
                .stream()
                .anyMatch(existingPet ->
                        existingPet.getId().equals(petId)
                );

        if (!alreadyAdded) {
            trip.getPets().add(pet);

            Trip savedTrip = tripRepository.save(trip);

            /*
             * New pet may introduce new applicable rules.
             */
            tripTaskService.recalculateTasksForTrip(savedTrip);

            return savedTrip;
        }

        return trip;
    }
}