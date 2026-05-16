package com.example.biskit.repo.vets;

import com.example.biskit.entities.Vets.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VetsRepo extends JpaRepository<Vet, Long>, JpaSpecificationExecutor<Vet> {
  Long countByEstadoFalse();

  Long countByEstadoTrue();
}
