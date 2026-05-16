package com.example.biskit.repo.pets;

import com.example.biskit.entities.Pets.Enfermedad;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnfermedadRepo extends JpaRepository<Enfermedad, Long> {
  Optional<Enfermedad> findByNombreIgnoreCase(String nombre);
}
