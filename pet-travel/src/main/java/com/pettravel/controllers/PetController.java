package com.pettravel.controllers;

import com.pettravel.models.Pet;
import com.pettravel.models.User;
import com.pettravel.repositories.PetRepository;
import com.pettravel.repositories.UserRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetController(
        PetRepository petRepository,
        UserRepository userRepository
    ) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {

        if (pet.getOwner() != null && pet.getOwner().getId() != null) {
            User owner = userRepository.findById(pet.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            pet.setOwner(owner);
        }
        return petRepository.save(pet);
    }

    @PutMapping("/{id}")
    public Pet updatePet(@PathVariable Long id, @RequestBody Pet updatedPet) {
        return petRepository.findById(id)
                .map(pet -> {
                    pet.setName(updatedPet.getName());
                    pet.setMicrochipNumber(updatedPet.getMicrochipNumber());
                    return petRepository.save(pet);
                })
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petRepository.deleteById(id);
    }
}