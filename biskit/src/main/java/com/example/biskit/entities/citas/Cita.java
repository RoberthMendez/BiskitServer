package com.example.biskit.entities.citas;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.vets.Vet;

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
public class Cita {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @Column(nullable = false)
  private LocalDateTime fechaHora;

  @ManyToOne
  @JoinColumn(name = "tipoCita_id")
  private TipoCita tipoCita;

  @ManyToOne
  @JoinColumn(name = "vet_id")
  private Vet vet;

  @ManyToOne
  @JoinColumn(name = "pet_id")
  private Pet pet;

}
