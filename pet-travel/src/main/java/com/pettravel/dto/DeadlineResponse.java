package com.pettravel.dto;

import java.time.LocalDate;

public class DeadlineResponse {
    
    private String ruleName;
    private LocalDate deadline;

    public DeadlineResponse(String ruleName, LocalDate deadline) {
        this.ruleName = ruleName;
        this.deadline = deadline;
    }

    public String getRuleName() {
        return ruleName;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}
