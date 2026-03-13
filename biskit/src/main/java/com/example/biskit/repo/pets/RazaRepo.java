package com.example.biskit.repo.pets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.biskit.entities.pets.Raza;

@Repository
public interface RazaRepo extends JpaRepository<Raza, Long> {

    Raza findByNombre(String nombre);
}
