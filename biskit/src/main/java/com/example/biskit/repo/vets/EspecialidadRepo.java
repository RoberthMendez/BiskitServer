package com.example.biskit.repo.vets;

import org.springframework.stereotype.Repository;

import com.example.biskit.entities.vets.Especialidad;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EspecialidadRepo extends JpaRepository<Especialidad, Long> {

}
