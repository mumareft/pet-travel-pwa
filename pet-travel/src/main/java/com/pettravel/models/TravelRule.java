package com.pettravel.models;

import jakarta.persistence.*;



@Entity 
public class TravelRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    private int daysBefore;

    public enum ReferenceType {
        DEPARTURE,
        RETURN
    }

    public TravelRule() {
    }

    public TravelRule(String name, String description, int daysBefore) {
        this.name = name;
        this.description = description;
        this.daysBefore = daysBefore;
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

    public int getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(int daysBefore) {
        this.daysBefore = daysBefore;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }
}
