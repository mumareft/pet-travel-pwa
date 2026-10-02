package com.pettravel.repositories;

import com.pettravel.models.CountryRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRuleRepository extends JpaRepository<CountryRule, Long> {

    Optional<CountryRule> findFirstByCountry_CodeIgnoreCaseOrCountry_NameIgnoreCase(
            String countryCode,
            String countryName);
}
