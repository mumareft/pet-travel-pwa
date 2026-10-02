package com.pettravel.dto;

import java.time.ZonedDateTime;

public class DeadlineWindow {

    private final ZonedDateTime earliest;
    private final ZonedDateTime latest;

    public DeadlineWindow(ZonedDateTime earliest, ZonedDateTime latest) {
        this.earliest = earliest;
        this.latest = latest;
    }

    public ZonedDateTime getEarliest() {
        return earliest;
    }

    public ZonedDateTime getLatest() {
        return latest;
    }
}
