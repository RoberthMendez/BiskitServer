package com.example.biskit.repo.pets;

import org.springframework.stereotype.Repository;

import com.example.biskit.entities.pets.Especie;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EspecieRepo extends JpaRepository<Especie, Long> {

    Especie findByNombre(String nombre);
}