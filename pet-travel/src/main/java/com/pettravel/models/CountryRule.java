package com.pettravel.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class CountryRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Country country;

    @ManyToMany
    @JoinTable(
            name = "country_rule_rules",
            joinColumns = @JoinColumn(name = "country_rule_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id"))
    private List<Rule> rules = new ArrayList<>();

    public CountryRule() {
    }

    public CountryRule(Country country, List<Rule> rules) {
        this.country = country;
        this.rules = rules;
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

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public void setRule(Rule rule) {
        this.rules.add(rule);
    }
}
