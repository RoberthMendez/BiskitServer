package com.example.biskit.repo;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.biskit.entities.Credenciales;

@Repository
public interface CredencialesRepo extends JpaRepository<Credenciales, Long> {

    boolean existsByUsuario(String usuario);
    Optional<Credenciales> findByUsuario(String usuario);
}
