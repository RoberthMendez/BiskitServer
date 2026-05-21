package com.example.biskit.repo.vets;

import com.example.biskit.entities.Vets.Especialidad;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepo extends JpaRepository<Especialidad, Long> {
  Optional<Especialidad> findByNombreIgnoreCase(String nombre);
}
