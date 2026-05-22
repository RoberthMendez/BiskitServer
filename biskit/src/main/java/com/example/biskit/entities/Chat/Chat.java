package com.example.biskit.entities.Chat;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
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
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chat")
    @Size(min = 2, max = 2)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ParticipanteChat> participantes;  

    @Column(name = "participantes_key", unique = true, length = 64)
    private String participantesKey;

    @PrePersist
    @PreUpdate
    private void actualizarParticipantesKey() {
        if (participantes == null || participantes.size() != 2) {
            participantesKey = null;
            return;
        }

        List<Long> participantesIds = participantes.stream()
                .map(participante -> participante.getCredenciales() != null ? participante.getCredenciales().getId() : null)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());

        if (participantesIds.size() != 2) {
            participantesKey = null;
            return;
        }

        participantesKey = participantesIds.get(0) + "-" + participantesIds.get(1);
    }

}