package com.pettravel.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "origin_id")
    private Country origin;

    @ManyToOne
    @JoinColumn (name = "destination_id")
    private Country destination;

    private LocalDateTime originDepartureDT;
    private LocalDateTime outboundArrivalDT;
    private LocalDateTime returnDepartureDT;
    private LocalDateTime returnArrivalDT;

    @ManyToMany
    @JoinTable(
            name = "trip_pets",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets = new ArrayList<>();

    public Trip() {
    }

    public Trip(
            User user,
            Country origin,
            Country destination,
            LocalDateTime originDepartureDT,
            LocalDateTime outboundArrivalDT,
            LocalDateTime returnDepartureDT,
            LocalDateTime returnArrivalDT,
            List<Pet> pets
    ) {
        this.user = user;
        this.origin = origin;
        this.destination = destination;
        this.originDepartureDT = originDepartureDT;
        this.outboundArrivalDT = outboundArrivalDT;
        this.returnDepartureDT = returnDepartureDT;
        this.returnArrivalDT = returnArrivalDT;
        this.pets = pets;
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

    public Country getOriginCountry() {
        return origin;
    }

    public void setOriginCountry(Country origin) {
        this.origin = origin;
    }

    public Country getDestinationCountry() {
        return destination;
    }

    public void setDestinationCountry(Country destination) {
        this.destination = destination;
    }

    public LocalDateTime getOriginDepartureDT() {
        return originDepartureDT;
    }

    public void setOriginDepartureDT(LocalDateTime originDepartureDT) {
        this.originDepartureDT = originDepartureDT;
    }

    public LocalDateTime getOutboundArrivalDT() {
        return outboundArrivalDT;
    }

    public void setOutboundArrivalDT(LocalDateTime outboundArrivalDT) {
        this.outboundArrivalDT = outboundArrivalDT;
    }

    public LocalDateTime getReturnDepartureDT() {
        return returnDepartureDT;
    }

    public void setReturnDepartureDT(LocalDateTime returnDepartureDT) {
        this.returnDepartureDT = returnDepartureDT;
    }

    public LocalDateTime getReturnArrivalDT() {
        return returnArrivalDT;
    }

    public void setReturnArrivalDT(LocalDateTime returnArrivalDT) {
        this.returnArrivalDT = returnArrivalDT;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}