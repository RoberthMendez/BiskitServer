package com.example.biskit.entities.citas;

import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.vets.Vet;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm a")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
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
