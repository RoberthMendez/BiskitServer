package com.example.biskit.entities.Chat;

import java.sql.Timestamp;

import org.apache.poi.hpsf.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mensaje {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private ParticipanteChat remitente;

        @Column(name = "contenido", nullable = false, length = 1000)
        private String contenido;

        @Column(name = "timestamp", nullable = false)
        private Timestamp timestamp;

}
