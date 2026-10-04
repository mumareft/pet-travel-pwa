package com.pettravel.dto;

import java.time.ZonedDateTime;

public class TaskResponse {

    private Long id;
    private String ruleName;
    private ZonedDateTime earliestDateTime;
    private ZonedDateTime latestDateTime;
    private boolean status;

    public TaskResponse(Long id, String ruleName, ZonedDateTime earliestDateTime, ZonedDateTime latestDateTime, boolean status) {
        this.id = id;
        this.ruleName = ruleName;
        this.earliestDateTime = earliestDateTime;
        this.latestDateTime = latestDateTime;
        this.status = status;
    }

    public Long getId() {
        return id;
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

    public boolean isCompleted() {
        return status;
    }
}
