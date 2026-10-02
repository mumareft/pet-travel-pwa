package com.pettravel.dto;

import java.time.ZonedDateTime;

public class DeadlineResponse {

    private String ruleName;
    private ZonedDateTime earliestDateTime;
    private ZonedDateTime latestDateTime;

    public DeadlineResponse(String ruleName, ZonedDateTime earliestDateTime, ZonedDateTime latestDateTime) {
        this.ruleName = ruleName;
        this.earliestDateTime = earliestDateTime;
        this.latestDateTime = latestDateTime;
    }

    public String getRuleName() {
        return ruleName;
    }

    public ZonedDateTime getEarliestDateTime() {
        return earliestDateTime;
    }

    public ZonedDateTime getLatestDateTime() {
        return latestDateTime;
    }
}
