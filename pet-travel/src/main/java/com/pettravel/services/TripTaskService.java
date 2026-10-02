package com.pettravel.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pettravel.dto.DeadlineWindow;
import com.pettravel.models.CountryRule;
import com.pettravel.models.Rule;
import com.pettravel.models.Trip;
import com.pettravel.models.TripTask;
import com.pettravel.repositories.CountryRuleRepository;
import com.pettravel.repositories.TripTaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TripTaskService {

    private final CountryRuleRepository countryRuleRepository;
    private final TripTaskRepository tripTaskRepository;
    private final TravelRuleService travelRuleService;

    public TripTaskService(
            CountryRuleRepository countryRuleRepository,
            TripTaskRepository tripTaskRepository,
            TravelRuleService travelRuleService) {
        this.countryRuleRepository = countryRuleRepository;
        this.tripTaskRepository = tripTaskRepository;
        this.travelRuleService = travelRuleService;
    }

    @Transactional
    public List<TripTask> createTasksForTrip(Trip trip) {
        String destination = trip.getDestinationCountry();
        CountryRule countryRule = countryRuleRepository
                .findFirstByCountry_CodeIgnoreCaseOrCountry_NameIgnoreCase(destination, destination)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No travel rules found for destination country: " + destination));

        // Delegate window calculation to TravelRuleService, then persist one task per rule.
        List<TripTask> tasks = countryRule.getRules().stream()
                .map(rule -> createTask(trip, countryRule, rule))
                .toList();

        return tripTaskRepository.saveAll(tasks);
    }

    @Transactional
    public List<TripTask> recalculateTasksForTrip(Trip trip) {
        tripTaskRepository.deleteByTrip_Id(trip.getId());
        return createTasksForTrip(trip);
    }

    @Transactional(readOnly = true)
    public List<TripTask> getTasksForTrip(Long tripId) {
        return tripTaskRepository.findByTrip_Id(tripId);
    }

    private TripTask createTask(Trip trip, CountryRule countryRule, Rule rule) {
        DeadlineWindow window = travelRuleService.calculateWindow(trip, countryRule, rule);
        return new TripTask(window.getEarliest(), window.getLatest(), rule, false, null, trip);
    }
}
