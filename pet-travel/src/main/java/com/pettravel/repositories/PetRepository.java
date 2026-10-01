package com.pettravel.repositories;

import com.pettravel.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Pet findByMicrochipNumber(String microchipNumber);
}