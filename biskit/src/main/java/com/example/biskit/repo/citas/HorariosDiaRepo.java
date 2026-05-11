package com.example.biskit.repo.citas;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.biskit.entities.citas.HorarioDia;

@Repository
public interface HorariosDiaRepo extends JpaRepository<HorarioDia, Long> {

}
