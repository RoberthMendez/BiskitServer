package com.example.biskit.repo.pets;

import com.example.biskit.entities.Pets.Raza;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RazaRepo extends JpaRepository<Raza, Long> {
  Raza findByNombre(String nombre);

  Optional<Raza> findByNombreIgnoreCase(String nombre);
}
