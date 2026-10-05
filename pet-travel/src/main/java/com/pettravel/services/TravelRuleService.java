package com.pettravel.services;

import org.springframework.stereotype.Service;

import com.pettravel.dto.DeadlineWindow;
import com.pettravel.models.Rule;
import com.pettravel.models.Trip;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service 
public class TravelRuleService {
    
    //Rules with simple deadline
    public ZonedDateTime calculateDeadline(Trip trip, Rule rule) {

        LocalDateTime referenceDate = getReferenceDate(trip, rule);

        ZoneId zone = rule.getCountry().getTimeZone();

        return referenceDate.atZone(zone).minusHours(rule.getEarliestHourBefore());
    }

    //Rules with valid time window
    public DeadlineWindow calculateWindow(Trip trip, Rule rule) {
        
        LocalDateTime referenceDate = getReferenceDate(trip, rule);

        ZoneId zone = rule.getCountry().getTimeZone();

        ZonedDateTime zonedReference = referenceDate.atZone(zone);

        return new DeadlineWindow(
            zonedReference.minusHours(rule.getEarliestHourBefore()),
            zonedReference.minusHours(rule.getLatestHourBefore())
        );
    }


    private LocalDateTime getReferenceDate(Trip trip, Rule rule) {
        return switch (rule.getReferenceType()) {
            case ORIGIN_DEPARTURE -> trip.getOriginDepartureDT();
            case OUTBOUND_ARRIVAL -> trip.getOutboundArrivalDT();
            case RETURN_DEPARTURE -> trip.getReturnDepartureDT();
            case RETURN_ARRIVAL -> trip.getReturnArrivalDT();
        };
    }
}

