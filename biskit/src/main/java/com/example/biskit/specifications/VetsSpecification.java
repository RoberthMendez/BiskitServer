package com.example.biskit.specifications;

import com.example.biskit.entities.DTOs.VetsFiltrosDto;
import com.example.biskit.entities.Vets.Vet;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class VetsSpecification {

  public static Specification<Vet> conFiltros(VetsFiltrosDto filtros) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      // ✅ ESTADO (columna directa)
      if (filtros.getEstado() != null) predicates.add(
        cb.equal(root.get("estado"), filtros.getEstado())
      );
      // SQL: WHERE estado = true

      // ✅ ESPECIALIDAD — es FK, requiere join
      if (filtros.getEspecialidad() != null && !filtros.getEspecialidad().isBlank()) {
        Join<Object, Object> especialidadJoin = root.join("especialidad", JoinType.LEFT);
        predicates.add(
          cb.like(
            cb.lower(especialidadJoin.get("nombre")),
            "%" + filtros.getEspecialidad().toLowerCase() + "%"
          )
        );
        // SQL: JOIN especialidad e ON e.id = especialidad_id
        // WHERE LOWER(e.nombre) LIKE '%cirugía%'
      }

      // ✅ CANTIDAD DE TRATAMIENTOS MÍNIMA
      if (filtros.getTratamientos() != null) predicates.add(
        cb.ge(cb.size(root.get("tratamientos")), filtros.getTratamientos())
      );
      // SQL: WHERE (SELECT COUNT(*) FROM tratamientos WHERE vet_id = v.id) >= N

      // ✅ MASCOTA TRATADA — por nombre en lugar de id
      if (filtros.getPet() != null && !filtros.getPet().isBlank()) {
        Join<Object, Object> tratamientosJoin = root.join("tratamientos", JoinType.INNER);
        predicates.add(
          cb.like(
            cb.lower(tratamientosJoin.get("pet").get("nombre")),
            "%" + filtros.getPet().toLowerCase() + "%"
          )
        );
        // SQL: JOIN tratamiento t ON t.vet_id = v.id
        // WHERE LOWER(t.pet_nombre) LIKE '%zeus%'
      }

      query.distinct(true);

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
