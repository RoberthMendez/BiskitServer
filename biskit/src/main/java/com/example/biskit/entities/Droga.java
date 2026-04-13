package com.example.biskit.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Droga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(name = "precio_compra", nullable = false, length = 255)
    private long precioCompra;

    @Column(name = "precio_venta", nullable = false, length = 255)
    private long precioVenta;

    @Column(name = "unidades_disponibles", nullable = false, length = 255)
    private int unidadesDisponibles;

    @Column(name = "unidades_vendidas", nullable = false, length = 255)
    private int unidadesVendidas;

    @JsonIgnore
    @ManyToMany(mappedBy = "drogas")
    private List<Tratamiento> tratamientos;
  
}
