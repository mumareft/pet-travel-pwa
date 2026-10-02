package com.pettravel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;    // DEPARTURE, RETURN, ARRIVAL

    private Integer daysBefore;
    private Integer earliestHourBefore;
    private Integer latestHourBefore;



    public Rule() {
    }

    public Rule(String name, String description, ReferenceType referenceType) {
        this.name = name;
        this.description = description;
        this.referenceType = referenceType;
    }

    public enum ReferenceType {
        DEPARTURE,
        RETURN,
        ARRIVAL
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(Integer daysBefore) {
        this.daysBefore = daysBefore;
    }

    public Integer getEarliestHourBefore() {
        return earliestHourBefore;
    }

    public void setEarliestHourBefore(Integer earliestHourBefore) {
        this.earliestHourBefore = earliestHourBefore;
    }

    public Integer getLatestHourBefore() {
        return latestHourBefore;
    }

    public void setLatestHourBefore(Integer latestHourBefore) {
        this.latestHourBefore = latestHourBefore;
    }
}
