package com.example.biskit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.biskit.service.Chat.ChatService;

import com.example.biskit.entities.Chat.Chat;
import com.example.biskit.entities.Chat.Mensaje;
import com.example.biskit.entities.Chat.ParticipanteChat;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {
    
    @Autowired
    private ChatService chatService;




    /* ===================================== CHAT ============================== */

    @PostMapping("/add")
    public ResponseEntity<Chat> addChat(@RequestBody Chat chat) {
        return new ResponseEntity<>(chatService.addChat(chat), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable Long id) {
        Chat chat = chatService.getChatById(id);
        if (chat != null) {
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chat> updateChat(@PathVariable Long id, @RequestBody Chat chat) {
        chat.setId(id);
        Chat updatedChat = chatService.updateChat(chat);
        if (updatedChat != null) {
            return new ResponseEntity<>(updatedChat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





    /* ===================================== PARTICIPANTE CHAT ============================== */

    @PostMapping("/addParticipanteChat")
    public ResponseEntity<ParticipanteChat> addParticipanteChat(@RequestBody ParticipanteChat participanteChat) {
        return new ResponseEntity<>(chatService.addParticipanteChat(participanteChat), HttpStatus.CREATED);
    }

    @GetMapping("/participanteChat/{id}")
    public ResponseEntity<ParticipanteChat> getParticipanteChatById(@PathVariable Long id) {
        ParticipanteChat participanteChat = chatService.getParticipanteChatById(id);
        if (participanteChat != null) {
            return new ResponseEntity<>(participanteChat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/participanteChat/{id}")
    public ResponseEntity<ParticipanteChat> updateParticipanteChat(@PathVariable Long id, @RequestBody ParticipanteChat participanteChat) {
        participanteChat.setId(id);
        ParticipanteChat updatedParticipanteChat = chatService.updateParticipanteChat(participanteChat);
        if (updatedParticipanteChat != null) {
            return new ResponseEntity<>(updatedParticipanteChat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/participanteChat/{id}")
    public ResponseEntity<Void> deleteParticipanteChat(@PathVariable Long id) {
        chatService.deleteParticipanteChat(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





    /* ===================================== MENSAJE ============================== */

    @PostMapping("/addMensaje")
    public ResponseEntity<Mensaje> addMensaje(@RequestBody Mensaje mensaje) {
        return new ResponseEntity<>(chatService.addMensaje(mensaje), HttpStatus.CREATED);
    }

    @GetMapping("/mensaje/{id}")
    public ResponseEntity<Mensaje> getMensajeById(@PathVariable Long id) {
        Mensaje mensaje = chatService.getMensajeById(id);
        if (mensaje != null) {
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/mensaje/{id}")
    public ResponseEntity<Mensaje> updateMensaje(@PathVariable Long id, @RequestBody Mensaje mensaje) {
        mensaje.setId(id);
        Mensaje updatedMensaje = chatService.updateMensaje(mensaje);
        if (updatedMensaje != null) {
            return new ResponseEntity<>(updatedMensaje, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/mensaje/{id}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable Long id) {
        chatService.deleteMensaje(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
