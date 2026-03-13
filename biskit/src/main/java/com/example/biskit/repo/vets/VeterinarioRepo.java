package com.example.biskit.repo.vets;

import org.springframework.stereotype.Repository;

import com.example.biskit.entities.vets.Veterinario;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VeterinarioRepo extends JpaRepository<Veterinario, Long> {

}