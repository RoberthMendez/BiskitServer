package com.example.biskit.repo.citas;

import com.example.biskit.entities.Citas.HorarioDia;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorariosDiaRepo extends JpaRepository<HorarioDia, Long> {
  Optional<HorarioDia> findByVetIdAndDiaSemana(Long vetId, String diaSemana);

  List<HorarioDia> findByVetId(Long vetId);

  void deleteByVetId(Long vetId);
}
