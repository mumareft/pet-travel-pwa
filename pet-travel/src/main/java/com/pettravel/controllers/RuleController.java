package com.pettravel.controllers;

import com.pettravel.models.Rule;
import com.pettravel.models.Airline;
import com.pettravel.repositories.AirlineRepository;
import com.pettravel.repositories.RuleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final RuleRepository ruleRepository;
    private final AirlineRepository airlineRepository;

    public RuleController(
            RuleRepository ruleRepository,
            AirlineRepository airlineRepository) {
        this.ruleRepository = ruleRepository;
        this.airlineRepository = airlineRepository;
    }

    @GetMapping
    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    }

    @PostMapping
    public Rule createRule(@RequestBody Rule rule) {
        if (rule.getAirline() != null) {
            rule.setAirline(getManagedAirline(rule.getAirline()));
        }
        return ruleRepository.save(rule);
    }

    @GetMapping("/{id}")
    public Rule getRuleById(@PathVariable Long id) {
        return ruleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found"));
    }

    @PutMapping("/{id}")
    public Rule updateRule(@PathVariable Long id, @RequestBody Rule updatedRule) {
        return ruleRepository.findById(id)
                .map(rule -> {
                    rule.setName(updatedRule.getName());
                    rule.setDescription(updatedRule.getDescription());
                    if (updatedRule.getAirline() != null) {
                        rule.setAirline(getManagedAirline(updatedRule.getAirline()));
                    }
                    return ruleRepository.save(rule);
                })
                .orElseThrow(() -> new IllegalArgumentException("Rule not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable Long id) {
        ruleRepository.deleteById(id);
    }

    private Airline getManagedAirline(Airline airline) {
        if (airline.getId() == null) {
            throw new IllegalArgumentException("Airline must have an id");
        }

        return airlineRepository.findById(airline.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Airline not found: " + airline.getId()
                ));
    }
}