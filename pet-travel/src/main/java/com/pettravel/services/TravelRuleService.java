package com.pettravel.services;

import org.springframework.stereotype.Service;

import com.pettravel.dto.DeadlineWindow;
import com.pettravel.models.Country;
import com.pettravel.models.Rule;
import com.pettravel.models.Trip;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service 
public class TravelRuleService {
    
    //Rules with simple deadline
    public LocalDateTime calculateDeadline(Trip trip, Rule rule) {

        LocalDateTime referenceDate = switch (rule.getReferenceType()) {
            case DEPARTURE -> trip.getDepartureDate();
            case RETURN -> trip.getReturnDate();
            case ARRIVAL -> trip.getArrivalDate();
        };

        return referenceDate.minusDays(rule.getDaysBefore());
    }

    // //Rules with valid time window
    // public DeadlineWindow calculateWindow(Trip trip, Rule rule) {
        
    //     Country country = trip.getDestinationCountry();
        
    //     LocalDateTime reference = switch (rule.getReferenceType()) {
    //         case DEPARTURE -> trip.getDepartureDate();
    //         case RETURN -> trip.getReturnDate();
    //         case ARRIVAL -> trip.getArrivalDate();
    //     };

    //     ZonedDateTime zonedReference = reference.atZone(country.getTimeZone());

    //     return new DeadlineWindow(
    //         zonedReference.minusHours(rule.getEarliestHourBefore()),
    //         zonedReference.minusHours(rule.getLatestHourBefore())
    //     );
    // }
}
