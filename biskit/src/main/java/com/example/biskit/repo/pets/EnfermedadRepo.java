package com.example.biskit.repo.pets;

import org.springframework.stereotype.Repository;

import com.example.biskit.entities.pets.Enfermedad;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EnfermedadRepo extends JpaRepository<Enfermedad, Long> {

    Enfermedad findByNombreIgnoreCase(String nombre);

}
