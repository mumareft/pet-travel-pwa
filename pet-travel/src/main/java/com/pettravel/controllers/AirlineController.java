package com.pettravel.controllers;

import com.pettravel.models.Airline;
import com.pettravel.repositories.AirlineRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineController {

    private final AirlineRepository airlineRepository;

    public AirlineController(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    @GetMapping
    public List<Airline> getAllAirlines() {
        return airlineRepository.findAll();
    }

    @GetMapping("/{id}")
    public Airline getAirlineById(@PathVariable Long id) {
        return airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found"));
    }

    @PostMapping
    public Airline createAirline(@RequestBody Airline airline) {
        return airlineRepository.save(airline);
    }

    @PutMapping("/{id}")
    public Airline updateAirline(
            @PathVariable Long id,
            @RequestBody Airline updatedAirline) {

        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found"));

        airline.setName(updatedAirline.getName());
        airline.setIataCode(updatedAirline.getIataCode());

        return airlineRepository.save(airline);
    }

    @DeleteMapping("/{id}")
    public void deleteAirline(@PathVariable Long id) {
        airlineRepository.deleteById(id);
    }
}
