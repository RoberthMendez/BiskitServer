package com.example.biskit.repo.pets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.biskit.entities.pets.Pet;

@Repository
public interface PetsRepo extends JpaRepository<Pet, Long> {

    Long countByEstadoFalse();

}
