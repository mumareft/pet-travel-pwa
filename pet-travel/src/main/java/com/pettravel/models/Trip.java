package com.pettravel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "origin_id", nullable = false)
    private Country origin;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Country destination;

    @ManyToOne
    @JoinColumn(name = "outbound_airline_id")
    private Airline outboundAirline;

    @ManyToOne
    @JoinColumn(name = "return_airline_id")
    private Airline returnAirline;

    private LocalDateTime originDepartureDT;
    private LocalDateTime originArrivalDT;
    private LocalDateTime destinationDepartureDT;
    private LocalDateTime destinationArrivalDT;

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
            LocalDateTime originArrivalDT,
            LocalDateTime destinationDepartureDT,
            LocalDateTime destinationArrivalDT,
            List<Pet> pets
    ) {
        this.user = user;
        this.origin = origin;
        this.destination = destination;
        this.originDepartureDT = originDepartureDT;
        this.originArrivalDT = originArrivalDT;
        this.destinationDepartureDT = destinationDepartureDT;
        this.destinationArrivalDT = destinationArrivalDT;
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


    public Country getOrigin() {
        return origin;
    }


    public void setOrigin(Country origin) {
        this.origin = origin;
    }


    public Country getDestination() {
        return destination;
    }


    public void setDestination(Country destination) {
        this.destination = destination;
    }

    public Airline getOutboundAirline() {
        return outboundAirline;
    }


    public void setOutboundAirline(Airline outboundAirline) {
        this.outboundAirline = outboundAirline;
    }


    public Airline getReturnAirline() {
        return returnAirline;
    }


    public void setReturnAirline(Airline returnAirline) {
        this.returnAirline = returnAirline;
    }

    @JsonProperty(value = "outboundAirlineId", access = JsonProperty.Access.WRITE_ONLY)
    public void setOutboundAirlineId(Long outboundAirlineId) {
        this.outboundAirline = airlineReference(outboundAirlineId);
    }


    @JsonProperty(value = "returnAirlineId", access = JsonProperty.Access.WRITE_ONLY)
    public void setReturnAirlineId(Long returnAirlineId) {
        this.returnAirline = airlineReference(returnAirlineId);
    }


    private Airline airlineReference(Long airlineId) {
        if (airlineId == null) {
            return null;
        }

        Airline selectedAirline = new Airline();
        selectedAirline.setId(airlineId);
        return selectedAirline;
    }


    public LocalDateTime getOriginDepartureDT() {
        return originDepartureDT;
    }


    public void setOriginDepartureDT(LocalDateTime originDepartureDT) {
        this.originDepartureDT = originDepartureDT;
    }


    public LocalDateTime getOriginArrivalDT() {
        return originArrivalDT;
    }


    public void setOriginArrivalDT(LocalDateTime originArrivalDT) {
        this.originArrivalDT = originArrivalDT;
    }


    public LocalDateTime getDestinationDepartureDT() {
        return destinationDepartureDT;
    }


    public void setDestinationDepartureDT(LocalDateTime destinationDepartureDT) {
        this.destinationDepartureDT = destinationDepartureDT;
    }


    public LocalDateTime getDestinationArrivalDT() {
        return destinationArrivalDT;
    }


    public void setDestinationArrivalDT(LocalDateTime destinationArrivalDT) {
        this.destinationArrivalDT = destinationArrivalDT;
    }


    public List<Pet> getPets() {
        return pets;
    }


    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }


    /*
     * Compatibility methods.
     *
     * Keep these temporarily in case TripTaskService or other existing
     * classes still use getOriginCountry() / getDestinationCountry().
     *
     * @JsonIgnore prevents Jackson from exposing duplicate JSON properties.
     */

    @JsonIgnore
    public Country getOriginCountry() {
        return origin;
    }


    @JsonIgnore
    public void setOriginCountry(Country origin) {
        this.origin = origin;
    }


    @JsonIgnore
    public Country getDestinationCountry() {
        return destination;
    }


    @JsonIgnore
    public void setDestinationCountry(Country destination) {
        this.destination = destination;
    }
}