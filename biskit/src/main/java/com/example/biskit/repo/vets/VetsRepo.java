package com.example.biskit.repo.vets;

import org.springframework.stereotype.Repository;

import com.example.biskit.entities.vets.Vet;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VetsRepo extends JpaRepository<Vet, Long> {

}