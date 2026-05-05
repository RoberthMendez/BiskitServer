package com.example.biskit.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.biskit.entities.dtos.VetsFiltrosDto;
import com.example.biskit.entities.vets.Vet;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class VetsSpecification {

    @SuppressWarnings("null")
    public static Specification<Vet> conFiltros(VetsFiltrosDto filtros) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // ✅ ESTADO (columna directa)
            if (filtros.getEstado() != null)
                predicates.add(cb.equal(root.get("estado"), filtros.getEstado()));
            // SQL: WHERE estado = true

            // ✅ ESPECIALIDAD — es FK, requiere join
            if (filtros.getEspecialidad() != null && !filtros.getEspecialidad().isBlank()) {
                Join<Object, Object> especialidadJoin = root.join("especialidad", JoinType.LEFT);
                predicates.add(cb.like(
                        cb.lower(especialidadJoin.get("nombre")),
                        "%" + filtros.getEspecialidad().toLowerCase() + "%"));
                // SQL: JOIN especialidad e ON e.id = especialidad_id
                // WHERE LOWER(e.nombre) LIKE '%cirugía%'
            }

            // ✅ CANTIDAD DE TRATAMIENTOS MÍNIMA
            if (filtros.getTratamientos() != null)
                predicates.add(cb.ge(
                        cb.size(root.get("tratamientos")),
                        filtros.getTratamientos()));
            // SQL: WHERE (SELECT COUNT(*) FROM tratamientos WHERE vet_id = v.id) >= N

            // ✅ MASCOTA TRATADA — por nombre en lugar de id
            if (filtros.getPet() != null && !filtros.getPet().isBlank()) {
                Join<Object, Object> tratamientosJoin = root.join("tratamientos", JoinType.INNER);
                predicates.add(cb.like(
                        cb.lower(tratamientosJoin.get("pet").get("nombre")),
                        "%" + filtros.getPet().toLowerCase() + "%"));
                // SQL: JOIN tratamiento t ON t.vet_id = v.id
                // WHERE LOWER(t.pet_nombre) LIKE '%zeus%'
            }

            if (filtros.getMisMascotas() != null && filtros.getMisMascotas()) {
                if (filtros.getVetId() != null) {
                    predicates.add(cb.equal(root.get("id"), filtros.getVetId()));
                    predicates.add(cb.greaterThan(cb.size(root.get("tratamientos")), 0));
                } else {
                    // sin vetId, devolver sólo veterinarios que tengan al menos un tratamiento
                    predicates.add(cb.greaterThan(cb.size(root.get("tratamientos")), 0));
                }
            }

            query.distinct(true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}