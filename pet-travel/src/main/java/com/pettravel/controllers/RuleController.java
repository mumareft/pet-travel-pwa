package com.pettravel.controllers;

import com.pettravel.models.Rule;
import com.pettravel.repositories.RuleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final RuleRepository ruleRepository;

    public RuleController(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @GetMapping
    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    }

    @PostMapping
    public Rule createRule(@RequestBody Rule rule) {
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
                    return ruleRepository.save(rule);
                })
                .orElseThrow(() -> new IllegalArgumentException("Rule not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable Long id) {
        ruleRepository.deleteById(id);
    }
}