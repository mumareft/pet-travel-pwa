package com.pettravel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User user;
    private Country origin;
    private Country destination;
    private LocalDateTime departureDT;
    private LocalDateTime arrivalDT;
    private LocalDateTime returnDT;

    private List<Pet> pets = new ArrayList<>(); 

    public Trip() {
    }

    public Trip(LocalDateTime departureDate, LocalDateTime returnDate, Country originCountry, Country destinationCountry, User user, List<Pet> pets) {
        this.user = user;
        this.pets = pets;
        this.departureDT = departureDate;
        this.returnDT = returnDate;
        this.origin = originCountry;
        this.destination = destinationCountry;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDepartureDate() {
        return departureDT;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDT = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDT;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDT = arrivalDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDT;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDT = returnDate;
    }

    public Country getOriginCountry() {
        return origin;
    }

    public void setOriginCountry(Country originCountry) {
        this.origin = originCountry;
    }

    public Country getDestinationCountry() {
        return destination;
    }

    public void setDestinationCountry(Country destinationCountry) {
        this.destination = destinationCountry;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}