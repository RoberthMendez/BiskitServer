package com.example.biskit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.biskit.entities.Pet;

@Repository
public interface PetsRepo extends JpaRepository<Pet, Long> {

}
