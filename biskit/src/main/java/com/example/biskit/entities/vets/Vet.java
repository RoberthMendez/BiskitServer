package com.example.biskit.entities.vets;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.Tratamiento;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private boolean estado;

    @Column(nullable = false, length = 255, unique = true)
    private String correo;

    @Column(nullable = false, length = 20, unique = true)
    private String cedula;

    @Column(length = 255)
    private String urlFoto;

    @OneToOne
    @JoinColumn(name = "credenciales_id")
    private Credenciales credenciales;

    @ManyToOne
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;

    @OneToMany(mappedBy = "vet")
    private List<Tratamiento> tratamientos;

}
