package com.example.biskit.repo;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.biskit.entities.Rol;

@Repository
public interface RolRepo extends JpaRepository<Rol, Long> {


    Rol findByNombre(String nombre);
}
