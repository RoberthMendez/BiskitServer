package com.example.biskit.specifications;

import com.example.biskit.entities.DTOs.PetsFiltrosDto;
import com.example.biskit.entities.Pets.Pet;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class PetsSpecification {

  public static Specification<Pet> conFiltros(PetsFiltrosDto filtros) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filtros.getEstado() != null) {
        if (filtros.getEstado()) {
          predicates.add(cb.isTrue(root.get("estado")));
        } else {
          predicates.add(cb.isFalse(root.get("estado")));
        }
      }

      if (filtros.getPeso() != null) predicates.add(
        cb.greaterThanOrEqualTo(root.get("peso"), filtros.getPeso())
      );

      if (filtros.getEdad() != null) {
        LocalDate fechaLimite = LocalDate.now().minusYears(filtros.getEdad());
        Date fechaLimiteSql = Date.valueOf(fechaLimite);
        predicates.add(cb.lessThanOrEqualTo(root.get("fechaNacimiento"), fechaLimiteSql));
      }

      if (filtros.getEspecie() != null || filtros.getRaza() != null) {
        Join<Object, Object> razaJoin = root.join("raza", JoinType.LEFT);

        if (filtros.getEspecie() != null) {
          Join<Object, Object> especieJoin = razaJoin.join("especie", JoinType.LEFT);
          predicates.add(
            cb.like(
              cb.lower(especieJoin.get("nombre")),
              "%" + filtros.getEspecie().toLowerCase() + "%"
            )
          );
        }

        if (filtros.getRaza() != null) predicates.add(
          cb.like(cb.lower(razaJoin.get("nombre")), "%" + filtros.getRaza().toLowerCase() + "%")
        );
      }

      if (filtros.getEnfermedad() != null) {
        Join<Object, Object> enfermedadJoin = root.join("enfermedad", JoinType.LEFT);
        predicates.add(
          cb.like(
            cb.lower(enfermedadJoin.get("nombre")),
            "%" + filtros.getEnfermedad().toLowerCase() + "%"
          )
        );
      }

      if (filtros.getTratamientos() != null) predicates.add(
        cb.greaterThanOrEqualTo(cb.size(root.get("tratamientos")), filtros.getTratamientos())
      );

      if (Boolean.TRUE.equals(filtros.getMisMascotas())) {
        if (filtros.getVetId() == null) {
          throw new IllegalArgumentException("vetId es obligatorio cuando misMascotas es true");
        }

        Join<Object, Object> tratamientosJoin = root.join("tratamientos", JoinType.INNER);
        Join<Object, Object> vetJoin = tratamientosJoin.join("vet", JoinType.INNER);
        predicates.add(cb.equal(vetJoin.get("id"), filtros.getVetId()));
      }

      query.distinct(true);
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
