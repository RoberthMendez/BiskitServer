package com.example.biskit.repo.pets;

import com.example.biskit.entities.Pets.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecieRepo extends JpaRepository<Especie, Long> {
  Especie findByNombre(String nombre);
}
