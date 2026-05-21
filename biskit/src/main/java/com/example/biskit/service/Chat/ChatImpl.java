package com.example.biskit.service.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.Chat.Chat;
import com.example.biskit.entities.Chat.Mensaje;
import com.example.biskit.entities.Chat.ParticipanteChat;

import com.example.biskit.repo.chat.ChatRepo;
import com.example.biskit.repo.chat.MensajeRepo;
import com.example.biskit.repo.chat.ParticipanteChatRepo;

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



    @Override
    public void addChat(Chat chat) {
        chatRepo.save(chat);
    }

    @Override
    public Chat getChatById(Long id) {
        return chatRepo.findById(id).orElse(null);
    }   

    @Override
    public void updateChat(Chat chat) {
        chatRepo.save(chat);
    }

    @Override
    public void deleteChat(Long id) {
        chatRepo.deleteById(id);
    }





    @Override
    public void addParticipanteChat(ParticipanteChat participanteChat) {
        participanteChatRepo.save(participanteChat);
    }

    @Override
    public ParticipanteChat getParticipanteChatById(Long id) {
        return participanteChatRepo.findById(id).orElse(null);
    }

    @Override
    public void updateParticipanteChat(ParticipanteChat participanteChat) {
        participanteChatRepo.save(participanteChat);
    }

    @Override
    public void deleteParticipanteChat(Long id) {
        participanteChatRepo.deleteById(id);
    }




    @Override
    public void addMensaje(Mensaje mensaje) {
        mensajeRepo.save(mensaje);
    }

    @Override
    public Mensaje getMensajeById(Long id) {
        return mensajeRepo.findById(id).orElse(null);
    }

    @Override
    public void updateMensaje(Mensaje mensaje) {
        mensajeRepo.save(mensaje);
    }

    @Override
    public void deleteMensaje(Long id) {
        mensajeRepo.deleteById(id);
    }

    @Override
    public void addParticipanteToChat(Long chatId, ParticipanteChat participanteChat) {
        Chat chat = chatRepo.findById(chatId).orElse(null);
        if (chat != null) {
            participanteChat.setChat(chat);
            participanteChatRepo.save(participanteChat);
        }
    }

    @Override
    public void addMensajeToParticipanteChat(String contenido, ParticipanteChat remitente) {
        Mensaje mensaje = Mensaje.builder()
                            .contenido(contenido)
                            .participanteChat(remitente)
                            .build();
        mensajeRepo.save(mensaje);
    }


    
}
