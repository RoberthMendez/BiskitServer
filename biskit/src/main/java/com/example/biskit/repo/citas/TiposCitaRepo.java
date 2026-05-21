package com.example.biskit.repo.citas;

import com.example.biskit.entities.Citas.TipoCita;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiposCitaRepo extends JpaRepository<TipoCita, Long> {
  Optional<TipoCita> findByNombre(String nombre);
}
