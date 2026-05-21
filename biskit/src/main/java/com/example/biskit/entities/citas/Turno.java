package com.example.biskit.entities.Citas;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalTime;
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
public class Turno {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String nombre;

  @DateTimeFormat(pattern = "hh:mm a")
  @JsonFormat(pattern = "hh:mm a", locale = "en_US")
  @Column(nullable = false)
  private LocalTime horaInicio;

  @DateTimeFormat(pattern = "hh:mm a")
  @JsonFormat(pattern = "hh:mm a", locale = "en_US")
  @Column(nullable = false)
  private LocalTime horaFin;
}
