package com.example.biskit.entities;

import com.example.biskit.entities.Pets.Pet;
import com.example.biskit.util.NoNormalizar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@ToString(exclude = "pets")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client extends EntidadBase implements Contactable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nombre", nullable = false, length = 100)
  private String nombre;

  @Column(name = "cedula", nullable = false, unique = true, length = 20)
  private String cedula;

  @Column(name = "correo", nullable = false, unique = true, length = 100)
  @NoNormalizar
  private String correo;

  @Column(name = "celular", nullable = false, length = 20)
  private String celular;

  @JsonIgnore
  @OneToOne
  @JoinColumn(name = "credenciales_id")
  private Credenciales credenciales;

  @JsonIgnore
  @OneToMany(mappedBy = "owner")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Pet> pets;
}
