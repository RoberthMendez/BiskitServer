package com.example.biskit.repo.pets;

import com.example.biskit.entities.Pets.Pet;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsRepo extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {
  Long countByEstadoTrue();

  Long countByEstadoFalse();

  @Query(
    "SELECT e.nombre, COUNT(p) FROM Pet p JOIN p.enfermedad e GROUP BY e.id, e.nombre ORDER BY COUNT(p) DESC, e.nombre ASC"
  )
  List<Object[]> findTop5Enfermedades(Pageable pageable);

  // Lista de mascotas tratadas por un veterinario específico
  List<Pet> findDistinctByTratamientosVetId(Long vetId);
}
