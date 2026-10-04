package com.pettravel.models;

import java.time.LocalDateTime;

import org.springframework.scheduling.config.Task;

public class Notification {
    
    private Long id;
    private Task task;
    private LocalDateTime scheduledDateTime;
    private boolean sent;

    public Notification() {
    }

    public Notification(Task task, LocalDateTime scheduledDateTime, boolean sent) {
        this.task = task;
        this.scheduledDateTime = scheduledDateTime;
        this.sent = sent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public LocalDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }

    public void setScheduledDateTime(LocalDateTime scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}


