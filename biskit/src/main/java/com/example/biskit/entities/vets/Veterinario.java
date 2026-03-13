package com.example.biskit.entities.vets;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import com.example.biskit.entities.Tratamiento;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private boolean estado;

    @Column(nullable = false, length = 20)
    private String cedula;

    @Column(nullable = false, length = 50, unique = true)
    private String usuario;

    @Column(nullable = false, length = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;

    @Column(length = 255)
    private String urlFoto;

    // Tratamientos
    @OneToMany(mappedBy = "veterinario")
    private List<Tratamiento> tratamientos;

}
