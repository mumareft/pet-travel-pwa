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
    private Country country;
    private String species;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;    // DEPARTURE, RETURN, ARRIVAL

    private Integer earliestHourBefore;
    private Integer latestHourBefore;

    @Enumerated(EnumType.STRING)
    private RuleDirection direction; // ENTRY, EXIT



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

    public enum RuleDirection {
        ENTRY,
        EXIT
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

    public RuleDirection getDirection() {
        return direction;
    }

    public void setDirection(RuleDirection direction) {
        this.direction = direction;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
