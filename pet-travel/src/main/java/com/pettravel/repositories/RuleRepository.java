package com.pettravel.repositories;

import com.pettravel.models.Country;
import com.pettravel.models.Pet.Species;
import com.pettravel.models.Rule;
import com.pettravel.models.Rule.RuleDirection;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RuleRepository extends JpaRepository<Rule, Long> {

    List<Rule> findByCountry(Country country);

    List<Rule> findByCountryAndSpecies(
            Country country,
            Species species
    );

    List<Rule> findByCountryAndSpeciesAndDirection(
            Country country,
            Species species,
            RuleDirection direction
    );

    @Query("""
    SELECT r
    FROM Rule r
    WHERE r.country = :country
      AND r.species = :species
      AND (r.breed IS NULL OR r.breed = :breed)
    """)
    List<Rule> findApplicableRules(
            Country country,
            Species species,
            String breed
    );
}
