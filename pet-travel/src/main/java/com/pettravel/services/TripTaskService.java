package com.pettravel.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pettravel.dto.DeadlineWindow;
import com.pettravel.dto.TaskResponse;
import com.pettravel.models.Pet;
import com.pettravel.models.Rule;
import com.pettravel.models.Trip;
import com.pettravel.models.TripTask;
import com.pettravel.repositories.RuleRepository;
import com.pettravel.repositories.TripTaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TripTaskService {

    private final TripTaskRepository tripTaskRepository;
    private final TravelRuleService travelRuleService;
    private final RuleRepository ruleRepository;

    public TripTaskService(
            TripTaskRepository tripTaskRepository,
            TravelRuleService travelRuleService,
            RuleRepository ruleRepository) {

        this.tripTaskRepository = tripTaskRepository;
        this.travelRuleService = travelRuleService;
        this.ruleRepository = ruleRepository;
    }

    @Transactional
    public List<TripTask> createTasksForTrip(Trip trip) {

        List<TripTask> tasks = new ArrayList<>();

        for (Pet pet : trip.getPets()) {

            List<Rule> originRules =
                    ruleRepository.findApplicableRules(
                            trip.getOrigin(),
                            pet.getSpecies(),
                            pet.getBreed()
                    );

            for (Rule rule : originRules) {
                tasks.add(createTask(trip, pet, rule));
            }

            List<Rule> destinationRules =
                    ruleRepository.findApplicableRules(
                            trip.getDestination(),
                            pet.getSpecies(),
                            pet.getBreed()
                    );

            for (Rule rule : destinationRules) {
                tasks.add(createTask(trip, pet, rule));
            }
        }

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

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long taskId) {

        TripTask task = tripTaskRepository.findById(taskId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "TripTask not found with id: " + taskId
                        )
                );

        return toTaskResponse(task);
    }

    private TripTask createTask(
            Trip trip,
            Pet pet,
            Rule rule) {

        DeadlineWindow window =
                travelRuleService.calculateWindow(trip, rule);

        return new TripTask(
                window.getEarliest(),
                window.getLatest(),
                rule,
                false,
                pet,
                trip
        );
    }

    @Transactional
    public TaskResponse updateTaskStatus(
            Long taskId,
            boolean isCompleted) {

        TripTask task = tripTaskRepository.findById(taskId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "TripTask not found with id: " + taskId
                        )
                );

        task.setStatus(isCompleted);

        TripTask savedTask =
                tripTaskRepository.save(task);

        return toTaskResponse(savedTask);
    }

    private TaskResponse toTaskResponse(TripTask task) {

        return new TaskResponse(
                task.getId(),
                task.getRule().getName(),
                task.getEarliestDateTime(),
                task.getLatestDateTime(),
                task.getPet(),
                task.isCompleted()
        );
    }
}