package com.pettravel.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.pettravel.dto.TaskResponse;
import com.pettravel.models.Pet;
import com.pettravel.models.Rule;
import com.pettravel.models.Trip;
import com.pettravel.models.TripTask;
import com.pettravel.models.TripTask.JourneyType;
import com.pettravel.repositories.RuleRepository;
import com.pettravel.repositories.TripTaskRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class TripTaskService {

	private final RuleRepository ruleRepository;
	private final TripTaskRepository tripTaskRepository;

	public TripTaskService(RuleRepository ruleRepository, TripTaskRepository tripTaskRepository) {
        this.ruleRepository = ruleRepository;
		this.tripTaskRepository = tripTaskRepository;
    }


    public List<TripTask> generateAllTasks(Trip trip) {
        List<TripTask> tasks = new ArrayList<>();
        for (Pet pet : trip.getPets()) {
            // OUTBOUND
            List<Rule> outboundRules =
                    ruleRepository.findCountryAndAirlineRules(
                            trip.getDestination(),
                            trip.getOutboundAirline(),
                            pet.getSpecies(),
                            pet.getBreed()
                    );
            for (Rule rule : outboundRules) {
                tasks.add(createTask(
                        trip,
                        pet,
                        rule,
                        JourneyType.OUTBOUND
                ));
            }
            // RETURN
            List<Rule> returnRules =
                    ruleRepository.findCountryAndAirlineRules(
                            trip.getOrigin(),
                            trip.getReturnAirline(),
                            pet.getSpecies(),
                            pet.getBreed()
                    );
            for (Rule rule : returnRules) {
                tasks.add(createTask(
                        trip,
                        pet,
                        rule,
                        JourneyType.RETURN
                ));
            }
        }
        return tripTaskRepository.saveAll(tasks);
    }


    private TripTask createTask(Trip trip, Pet pet, Rule rule, JourneyType journeyType) {
        LocalDateTime dueDate = calculateDueDate(trip, rule, journeyType);
        return new TripTask(
                trip,
                pet,
                rule,
                dueDate,
                false,
                journeyType
        );
    }


	public List<TripTask> recalculateTasksForTrip(Trip trip) {
		tripTaskRepository.deleteByTrip_Id(trip.getId());
		return generateAllTasks(trip);
	}


    private LocalDateTime calculateDueDate(Trip trip, Rule rule, JourneyType journeyType) {
        LocalDateTime date;
        if (journeyType == JourneyType.OUTBOUND) {
            date = trip.getOriginDepartureDT();
        } else {
            date = trip.getDestinationDepartureDT();
        }
        return date.minusHours(rule.getHoursBefore());
    }


	public TaskResponse getTaskById(Long taskId) {
		TripTask task = tripTaskRepository.findById(taskId)
				.orElseThrow(() ->
						new EntityNotFoundException("Task not found: " + taskId)
				);
		return toTaskResponse(task);
	}


	public TaskResponse updateTaskStatus(Long taskId, boolean isCompleted) {
		TripTask task = tripTaskRepository.findById(taskId)
				.orElseThrow(() ->
						new EntityNotFoundException("Task not found: " + taskId)
				);
		task.setCompleted(isCompleted);
		TripTask savedTask = tripTaskRepository.save(task);
		return toTaskResponse(savedTask);
	}


	private TaskResponse toTaskResponse(TripTask task) {
		return new TaskResponse(
				task.getId(),
				task.getRule().getTitle(),
				task.getDueDate(),
				task.getPet(),
				task.isCompleted()
		);
	}
}