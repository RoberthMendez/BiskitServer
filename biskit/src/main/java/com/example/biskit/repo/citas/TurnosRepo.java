package com.example.biskit.repo.citas;

import com.example.biskit.entities.Citas.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnosRepo extends JpaRepository<Turno, Long> {}
