package com.example.biskit.service.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.Chat.Chat;
import com.example.biskit.entities.Chat.Mensaje;
import com.example.biskit.entities.Chat.ParticipanteChat;

import com.example.biskit.repo.chat.ChatRepo;
import com.example.biskit.repo.chat.MensajeRepo;
import com.example.biskit.repo.chat.ParticipanteChatRepo;
import com.example.biskit.service.Credenciales.CredencialesService;

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
                            .participanteChat(remitente)
                            .build();
        mensajeRepo.save(mensaje);
    }



    @Override
    public void setCredencialesToParticipanteChat(ParticipanteChat participanteChat) {
        Long credencialesId = participanteChat.getCredenciales() != null ? participanteChat.getCredenciales().getId() : null;
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
                .map(participante -> participante.getCredenciales() != null ? participante.getCredenciales().getId() : null)
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

    
}
