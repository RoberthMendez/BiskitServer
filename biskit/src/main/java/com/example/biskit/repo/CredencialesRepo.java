package com.example.biskit.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.biskit.entities.Credenciales;

@Repository
public interface CredencialesRepo extends JpaRepository<Credenciales, Long> {

}
