package com.example.biskit.service.Chat;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.Chat.Chat;
import com.example.biskit.entities.Chat.Mensaje;
import com.example.biskit.entities.Chat.ParticipanteChat;
import com.example.biskit.entities.DTOs.Chat.MensajeDTO.MensajeDTO;
import com.example.biskit.entities.Vets.Vet;
import com.example.biskit.repo.chat.ChatRepo;
import com.example.biskit.repo.chat.MensajeRepo;
import com.example.biskit.repo.chat.ParticipanteChatRepo;
import com.example.biskit.service.Clients.ClientsService;
import com.example.biskit.service.Credenciales.CredencialesService;
import com.example.biskit.service.Vets.VetService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ChatImpl implements ChatService {

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private ParticipanteChatRepo participanteChatRepo;

    @Autowired
    private MensajeRepo mensajeRepo;

    @Autowired
    private CredencialesService credencialesService;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private VetService vetService;

    @Override
    public Chat addChat(Chat chat) {
        validarYAsignarClaveUnica(chat);
        Chat chatConRelaciones = asignarRelacionesDeChatPorIds(chat);
        return chatRepo.save(chatConRelaciones);
    }

    @Override
    public Chat getChatById(Long id) {
        return chatRepo.findById(id).orElse(null);
    }

    @Override
    public Chat updateChat(Chat chat) {
        validarYAsignarClaveUnica(chat);
        return chatRepo.save(chat);
    }

    @Override
    public void deleteChat(Long id) {
        chatRepo.deleteById(id);
    }

    @Override
    public ParticipanteChat addParticipanteChat(ParticipanteChat participanteChat) {
        return participanteChatRepo.save(participanteChat);
    }

    @Override
    public ParticipanteChat getParticipanteChatById(Long id) {
        return participanteChatRepo.findById(id).orElse(null);
    }

    @Override
    public ParticipanteChat updateParticipanteChat(ParticipanteChat participanteChat) {
        return participanteChatRepo.save(participanteChat);
    }

    @Override
    public void deleteParticipanteChat(Long id) {
        participanteChatRepo.deleteById(id);
    }

    @Override
    public Mensaje addMensaje(Mensaje mensaje) {
        return mensajeRepo.save(mensaje);
    }

    @Override
    public Mensaje getMensajeById(Long id) {
        return mensajeRepo.findById(id).orElse(null);
    }

    @Override
    public Mensaje updateMensaje(Mensaje mensaje) {
        return mensajeRepo.save(mensaje);
    }

    @Override
    public void deleteMensaje(Long id) {
        mensajeRepo.deleteById(id);
    }

    @Override
    public void addParticipanteToChat(ParticipanteChat participanteChat, Chat chat) {
        if (chat.getParticipantes() == null) {
            chat.setParticipantes(new ArrayList<>());
        }

        chat.getParticipantes().add(participanteChat);
        participanteChat.setChat(chat);
        participanteChatRepo.save(participanteChat);
        validarYAsignarClaveUnica(chat);
        chatRepo.save(chat);
    }

    @Override
    public void addMensajeToParticipanteChat(String contenido, ParticipanteChat remitente) {
        Mensaje mensaje = Mensaje.builder()
                .contenido(contenido)
                .remitente(remitente)
                .build();
        mensajeRepo.save(mensaje);
    }

    @Override
    public void setCredencialesToParticipanteChat(ParticipanteChat participanteChat) {
        Long credencialesId = participanteChat.getCredenciales() != null ? participanteChat.getCredenciales().getId()
                : null;
        Credenciales cred = credencialesService.getCredencialesById(credencialesId);
        participanteChat.setCredenciales(cred);
        participanteChatRepo.save(participanteChat);
    }

    @Override
    public Chat asignarRelacionesDeChatPorIds(Chat chat) {
        if (chat.getParticipantes() != null) {
            for (ParticipanteChat participante : chat.getParticipantes()) {
                participante.setChat(chat);
                if (participante.getCredenciales() != null)
                    setCredencialesToParticipanteChat(participante);
            }
        }
        return chat;
    }

    @Override
    public Long getChatIdByParticipantes(Long clientId, Long veterinarioId) {
        Client client = clientsService.getClientById(clientId);
        Vet vet = vetService.getVetById(veterinarioId);

        if (client == null || vet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente o veterinario no encontrado");
        }

        Long credencialesId1 = client.getCredenciales() != null ? client.getCredenciales().getId() : null;
        Long credencialesId2 = vet.getCredenciales() != null ? vet.getCredenciales().getId() : null;

        String participantesKey = construirParticipantesKey(credencialesId1, credencialesId2);
        Optional<Chat> chatExistente = chatRepo.findByParticipantesKey(participantesKey);
        return chatExistente.map(Chat::getId).orElse(null);
    }

    private String construirParticipantesKey(Long credencialesId1, Long credencialesId2) {
        if (credencialesId1 == null || credencialesId2 == null) {
            return null;
        }

        List<Long> participantesIds = List.of(credencialesId1, credencialesId2).stream()
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());

        if (participantesIds.size() != 2) {
            return null;
        }

        if (participantesIds.get(0).equals(participantesIds.get(1))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Un chat debe tener dos participantes distintos");
        }

        return participantesIds.get(0) + "-" + participantesIds.get(1);
    }

    private void validarYAsignarClaveUnica(Chat chat) {
        String participantesKey = construirParticipantesKey(chat);
        if (participantesKey == null) {
            chat.setParticipantesKey(null);
            return;
        }

        Optional<Chat> chatExistente = chatRepo.findByParticipantesKey(participantesKey);
        if (chatExistente.isPresent() && (chat.getId() == null || !chatExistente.get().getId().equals(chat.getId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un chat con estos dos participantes");
        }

        chat.setParticipantesKey(participantesKey);
    }

    private String construirParticipantesKey(Chat chat) {
        if (chat.getParticipantes() == null || chat.getParticipantes().size() != 2) {
            return null;
        }

        List<Long> participantesIds = chat.getParticipantes().stream()
                .map(participante -> participante.getCredenciales() != null ? participante.getCredenciales().getId()
                        : null)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());

        if (participantesIds.size() != 2) {
            return null;
        }

        if (participantesIds.get(0).equals(participantesIds.get(1))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Un chat debe tener dos participantes distintos");
        }

        return participantesIds.get(0) + "-" + participantesIds.get(1);
    }

    @Override
    public Mensaje sendMensaje(Long chatId, MensajeDTO mensajeDTO) {
        Chat chat = chatRepo.findById(chatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat no encontrado"));

        System.out.println(chat.getParticipantes().stream()
                .map(participante -> participante.getCredenciales() != null ? participante.getCredenciales().getId()
                        : null)
                .collect(Collectors.toList()));
        System.out.println("Remitente ID: " + mensajeDTO.getRemitenteId());

        ParticipanteChat remitente = chat.getParticipantes().stream()
                .filter(participante -> participante.getCredenciales() != null
                        && participante.getCredenciales().getId().equals(mensajeDTO.getRemitenteId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Remitente no encontrado en este chat"));

        if (!chat.getParticipantes().contains(remitente)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El remitente no es parte de este chat");
        }

        // String a Timestamp (acepta ISO 8601 con Z, offset, ISO_DATE_TIME, y el
        // formato yyyy-MM-dd HH:mm:ss)
        Timestamp timestamp = parseTimestampOrThrow(mensajeDTO.getTimestamp());

        Mensaje mensaje = Mensaje.builder()
                .contenido(mensajeDTO.getContenido())
                .remitente(remitente)
                .timestamp(timestamp)
                .build();
        remitente.getMensajesEnviados().add(mensaje);
        participanteChatRepo.save(remitente);

        return mensajeRepo.save(mensaje);
    }

    private Timestamp parseTimestampOrThrow(String ts) {
        if (ts == null || ts.isBlank()) {
            return new Timestamp(System.currentTimeMillis());
        }

        // 1) Try Instant (ISO_INSTANT, e.g. 2026-05-26T09:13:50.838Z)
        try {
            Instant instant = Instant.parse(ts);
            return Timestamp.from(instant);
        } catch (DateTimeParseException ignored) {
        }

        // 2) Try OffsetDateTime (e.g. 2026-05-26T09:13:50+02:00)
        try {
            OffsetDateTime odt = OffsetDateTime.parse(ts);
            return Timestamp.from(odt.toInstant());
        } catch (DateTimeParseException ignored) {
        }

        // 3) Try LocalDateTime with ISO_DATE_TIME (e.g. 2026-05-26T09:13:50 or with
        // fractional seconds)
        try {
            LocalDateTime ldt = LocalDateTime.parse(ts, DateTimeFormatter.ISO_DATE_TIME);
            return Timestamp.valueOf(ldt);
        } catch (DateTimeParseException ignored) {
        }

        // 4) Fallback to Timestamp.valueOf which expects 'yyyy-[m]m-[d]d
        // hh:mm:ss[.f...]'
        try {
            return Timestamp.valueOf(ts);
        } catch (IllegalArgumentException ex) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Formato de timestamp inválido. Use ISO 8601 (ej: 2026-05-26T09:13:50.838Z) o 'yyyy-MM-dd HH:mm:ss'");
        }
    }

}
