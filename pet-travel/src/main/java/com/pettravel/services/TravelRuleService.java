package com.pettravel.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service 
public class TravelRuleService {
    
    public LocalDate calculateVetCheckDate(LocalDate departureDate) {
        // Calculate the vet check date based on the departure date
        // For example, let's say the vet check must be done at least 7 days before departure
        return departureDate.minusDays(7);
    }
}
