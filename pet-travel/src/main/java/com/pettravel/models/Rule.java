package com.pettravel.models;

import com.pettravel.models.Pet.Species;
import jakarta.persistence.*;

@Entity
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Enumerated(EnumType.STRING)
    private Species species;

    private String breed;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    private Integer earliestHourBefore;

    private Integer latestHourBefore;

    @Enumerated(EnumType.STRING)
    private RuleDirection direction;

    public Rule() {
    }

    public Rule(String name, String description, ReferenceType referenceType) {
        this.name = name;
        this.description = description;
        this.referenceType = referenceType;
    }

    public enum ReferenceType {
        ORIGIN_DEPARTURE,
        OUTBOUND_ARRIVAL,
        RETURN_DEPARTURE,
        RETURN_ARRIVAL
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
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
}