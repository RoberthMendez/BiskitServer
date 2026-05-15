package com.example.biskit.repo;

import com.example.biskit.entities.Credenciales;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialesRepo extends JpaRepository<Credenciales, Long> {
  boolean existsByUsername(String username);
  Optional<Credenciales> findByUsername(String username);
}
