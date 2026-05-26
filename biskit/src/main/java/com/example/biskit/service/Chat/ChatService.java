package com.example.biskit.service.Chat;

import com.example.biskit.entities.Chat.Chat;
import com.example.biskit.entities.Chat.ParticipanteChat;
import com.example.biskit.entities.DTOs.Chat.MensajeDTO.MensajeDTO;
import com.example.biskit.entities.Chat.Mensaje;

public interface ChatService {

    public Chat addChat(Chat chat);

    public Chat addChat(Long idCliente, Long idVeterinario);

    public Chat getChatById(Long id);

    public Chat updateChat(Chat chat);

    public void deleteChat(Long id);

    public ParticipanteChat addParticipanteChat(ParticipanteChat participanteChat);

    public ParticipanteChat getParticipanteChatById(Long id);

    public ParticipanteChat updateParticipanteChat(ParticipanteChat participanteChat);

    public void deleteParticipanteChat(Long id);

    public Mensaje addMensaje(Mensaje mensaje);

    public Mensaje getMensajeById(Long id);

    public Mensaje updateMensaje(Mensaje mensaje);

    public void deleteMensaje(Long id);

    public void addParticipanteToChat(ParticipanteChat participanteChat, Chat chat);

    public void addMensajeToParticipanteChat(String contenido, ParticipanteChat remitente);

    public void setCredencialesToParticipanteChat(ParticipanteChat participanteChat);

    public Chat asignarRelacionesDeChatPorIds(Chat chat);

    public Long getChatIdByParticipantes(Long credencialesId1, Long credencialesId2);

    public Mensaje sendMensaje(Long chatId, MensajeDTO mensajeDTO);
}
