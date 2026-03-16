package com.example.biskit.repo.vets;

import org.springframework.stereotype.Repository;

import com.example.biskit.entities.vets.Especialidad;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface EspecialidadRepo extends JpaRepository<Especialidad, Long> {
    
    Optional<Especialidad> findByNombreIgnoreCase(String nombre);

}
