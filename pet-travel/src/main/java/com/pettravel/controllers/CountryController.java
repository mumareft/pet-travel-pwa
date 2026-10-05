package com.pettravel.controllers;

import com.pettravel.models.Country;
import com.pettravel.repositories.CountryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
    }

    @PostMapping
    public Country createCountry(@RequestBody Country country) {
        return countryRepository.save(country);
    }

    @PutMapping("/{id}")
    public Country updateCountry(
            @PathVariable Long id,
            @RequestBody Country updatedCountry) {

        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));

        country.setName(updatedCountry.getName());
        country.setCode(updatedCountry.getCode());
        country.setTimeZone(updatedCountry.getTimeZone());

        return countryRepository.save(country);
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) {
        countryRepository.deleteById(id);
    }
}