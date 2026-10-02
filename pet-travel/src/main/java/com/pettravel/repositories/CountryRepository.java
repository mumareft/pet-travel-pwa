package com.pettravel.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pettravel.models.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
