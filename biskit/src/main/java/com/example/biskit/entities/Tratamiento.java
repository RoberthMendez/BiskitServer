package com.example.biskit.entities;

import com.example.biskit.entities.vets.Vet;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;

import com.example.biskit.entities.pets.Pet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

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

    @Column(nullable = false, length = 255)
    private Date fecha;

    @ManyToOne
    private Pet pet;

    @ManyToOne
    private Vet vet;

    @ManyToMany
    @Builder.Default
    private List<Droga> drogas = new ArrayList<>();
}
