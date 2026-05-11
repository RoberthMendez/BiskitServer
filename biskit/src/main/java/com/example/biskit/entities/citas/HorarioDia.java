package com.example.biskit.entities.citas;

import com.example.biskit.entities.vets.Vet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HorarioDia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "turno_id", nullable = false)
  private Turno turno;

  @ManyToOne
  @JoinColumn(name = "vet_id", nullable = false)
  @JsonIgnore
  private Vet vet;

  @Column(nullable = false)
  private String diaSemana;
  
}
