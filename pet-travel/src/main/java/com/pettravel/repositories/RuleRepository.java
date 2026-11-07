package com.pettravel.repositories;

import com.pettravel.models.Country;
import com.pettravel.models.Airline;
import com.pettravel.models.Pet.Species;
import com.pettravel.models.Rule;
// import com.pettravel.models.Rule.RuleDirection;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository 
public interface RuleRepository extends JpaRepository<Rule, Long> {

	List<Rule> findByCountry(Country country);

	List<Rule> findByCountryAndSpecies(
		Country country,
		Species species
	);

	@Query("""
		SELECT r
		FROM Rule r
		WHERE r.species = :species
		AND (r.breed IS NULL OR r.breed = :breed)
		AND (
			r.country = :country
			OR r.airline = :airline
		)
	""")
	List<Rule> findCountryAndAirlineRules(
		Country country,
		Airline airline,
		Species species,
		String breed
	);




	@Query("""
		SELECT r
		FROM Rule r
		WHERE r.country = :country
		AND r.species = :species
		AND (r.breed IS NULL OR r.breed = :breed)
	""")
	List<Rule> findCountryRules(
		Country country,
		Species species,
		String breed
	);

	@Query("""
		SELECT r
		FROM Rule r
		WHERE r.airline = :airline
		AND r.species = :species
		AND (r.breed IS NULL OR r.breed = :breed)
	""")
	List<Rule> findAirlineRules(
		Airline airline,
		Species species,
		String breed
	);
}
