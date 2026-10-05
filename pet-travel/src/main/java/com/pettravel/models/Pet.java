package com.pettravel.models;

import jakarta.persistence.*;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Species species;

    private String breed;
    
    private String microchipNumber;

    @ManyToOne (optional = false)
    @JoinColumn (name = "owner_id", nullable = false)
    private User owner;

    public Pet() {
    }

    public Pet(String name, String microchipNumber, Species species, String breed) {
        this.name = name;
        this.microchipNumber = microchipNumber;
        this.species = species;
        this.breed = breed;
    }

    public enum Species {
        DOG,
        CAT,
        BIRD,
        REPTILE,
        RODENT,
        OTHER
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

    public String getMicrochipNumber() {
        return microchipNumber;
    }

    public void setMicrochipNumber(String microchipNumber) {
        this.microchipNumber = microchipNumber;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}