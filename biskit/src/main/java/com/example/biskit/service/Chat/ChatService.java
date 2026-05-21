package com.example.biskit.service.Chat;

import com.example.biskit.entities.Chat.Chat;
import com.example.biskit.entities.Chat.ParticipanteChat;
import com.example.biskit.entities.Chat.Mensaje;

public interface ChatService {

    public void addChat(Chat chat);
    public Chat getChatById(Long id);
    public void updateChat(Chat chat);
    public void deleteChat(Long id);

    public void addParticipanteChat(ParticipanteChat participanteChat);
    public ParticipanteChat getParticipanteChatById(Long id);
    public void updateParticipanteChat(ParticipanteChat participanteChat);
    public void deleteParticipanteChat(Long id);

    public void addMensaje(Mensaje mensaje);
    public Mensaje getMensajeById(Long id);
    public void updateMensaje(Mensaje mensaje);
    public void deleteMensaje(Long id);

    public void addParticipanteToChat(Long chatId, ParticipanteChat participanteChat);
    public void addMensajeToParticipanteChat(String contenido, ParticipanteChat remitente);
}
