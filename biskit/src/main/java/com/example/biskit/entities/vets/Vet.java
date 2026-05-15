package com.example.biskit.entities.vets;

import com.example.biskit.entities.Contactable;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.EntidadBase;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.citas.HorarioDia;
import com.example.biskit.util.NoNormalizar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Vet extends EntidadBase implements Contactable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String nombre;

  @Column(nullable = false)
  private boolean estado;

  @Column(nullable = false, length = 255, unique = true)
  @NoNormalizar
  private String correo;

  @Column(nullable = false, length = 20, unique = true)
  private String cedula;

  @Column(length = 255)
  @NoNormalizar
  private String urlFoto;

  @OneToOne
  @JoinColumn(name = "credenciales_id")
  private Credenciales credenciales;

  @ManyToOne
  @JoinColumn(name = "especialidad_id")
  private Especialidad especialidad;

  @OneToMany(mappedBy = "vet")
  @JsonIgnore
  private List<Tratamiento> tratamientos;

  @OneToMany(mappedBy = "vet")
  @JsonIgnore
  private List<HorarioDia> horariosDia;
}
