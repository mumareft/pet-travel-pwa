package com.pettravel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pettravel.models.TripTask;

public interface TripTaskRepository extends JpaRepository<TripTask, Long> {

    List<TripTask> findByTrip_Id(Long tripId);

    void deleteByTrip_Id(Long tripId);
}
