package com.pettravel.services;

import org.springframework.stereotype.Service;

import com.pettravel.models.TravelRule;
import com.pettravel.models.Trip;

import java.time.LocalDate;

@Service 
public class TravelRuleService {
    
    public LocalDate calculateDeadline(Trip trip, TravelRule rule) {

        LocalDate referenceDate;
        
        if (rule.getReferenceType() == TravelRule.ReferenceType.DEPARTURE) {
            referenceDate = trip.getDepartureDate();
        } else {
            referenceDate = trip.getReturnDate();
        }
        return referenceDate.minusDays(rule.getDaysBefore());
    }
}
