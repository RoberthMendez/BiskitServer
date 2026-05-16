package com.example.biskit.entities;

import com.example.biskit.entities.Pets.Pet;
import com.example.biskit.entities.Vets.Vet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tratamiento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(nullable = false)
  private LocalDate fecha;

  @ManyToOne
  private Pet pet;

  @ManyToOne
  private Vet vet;

  @ManyToMany
  @Builder.Default
  private List<Droga> drogas = new ArrayList<>();
}
