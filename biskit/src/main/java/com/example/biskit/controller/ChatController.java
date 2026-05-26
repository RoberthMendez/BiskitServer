package com.example.biskit.controller;

import com.example.biskit.entities.Chat.Chat;
import com.example.biskit.entities.Chat.Mensaje;
import com.example.biskit.entities.Chat.ParticipanteChat;
import com.example.biskit.service.Chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.biskit.service.Chat.ChatService;

import com.example.biskit.entities.Chat.Chat;
import com.example.biskit.entities.Chat.Mensaje;
import com.example.biskit.entities.Chat.ParticipanteChat;
import com.example.biskit.entities.DTOs.Chat.ParticipanteChatDTO.ParticipanteChatDTO;
import com.example.biskit.entities.DTOs.Chat.ParticipanteChatDTO.ParticipanteChatMapper;
import com.example.biskit.entities.DTOs.Chat.ChatDTO.ChatDTO;
import com.example.biskit.entities.DTOs.Chat.ChatDTO.ChatMapper;
import com.example.biskit.entities.DTOs.Chat.MensajeDTO.MensajeMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/chat")
public class ChatController {

  @Autowired
  private ChatService chatService;

  /* ===================================== CHAT ============================== */

  @PostMapping("/add")
  public ResponseEntity<ChatDTO> addChat(@RequestBody Chat chat) {
    return new ResponseEntity<>(
        ChatMapper.INSTANCE.toDTO(chatService.addChat(chat),
            ParticipanteChatMapper.INSTANCE.toDTOList(chat.getParticipantes()),
            MensajeMapper.INSTANCE.toDTOList(chat.getParticipantes().stream()
                .flatMap(p -> p.getMensajesEnviados().stream()).toList())),
        HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ChatDTO> getChatById(@PathVariable Long id) {
    Chat chat = chatService.getChatById(id);
    if (chat != null) {
      return new ResponseEntity<>(ChatMapper.INSTANCE.toDTO(chat,
          ParticipanteChatMapper.INSTANCE.toDTOList(chat.getParticipantes()),
          MensajeMapper.INSTANCE.toDTOList(
              chat.getParticipantes().stream().flatMap(p -> p.getMensajesEnviados().stream()).toList())),
          HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<ChatDTO> updateChat(@PathVariable Long id, @RequestBody Chat chat) {
    chat.setId(id);
    Chat updatedChat = chatService.updateChat(chat);
    if (updatedChat != null) {
      return new ResponseEntity<>(ChatMapper.INSTANCE.toDTO(updatedChat,
          ParticipanteChatMapper.INSTANCE.toDTOList(updatedChat.getParticipantes()),
          MensajeMapper.INSTANCE.toDTOList(updatedChat.getParticipantes().stream()
              .flatMap(p -> p.getMensajesEnviados().stream()).toList())),
          HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
    chatService.deleteChat(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/participanteChat/{id}")
  public ResponseEntity<ParticipanteChatDTO> updateParticipanteChat(@PathVariable Long id,
      @RequestBody ParticipanteChat participanteChat) {
    participanteChat.setId(id);
    ParticipanteChat updatedParticipanteChat = chatService.updateParticipanteChat(participanteChat);
    if (updatedParticipanteChat != null) {
      return new ResponseEntity<>(ParticipanteChatMapper.INSTANCE.toDTO(updatedParticipanteChat), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/participanteChat/{id}")
  public ResponseEntity<Void> deleteParticipanteChat(@PathVariable Long id) {
    chatService.deleteParticipanteChat(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /*
   * ===================================== MENSAJE ==============================
   */

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
  public ResponseEntity<Mensaje> updateMensaje(
      @PathVariable Long id,
      @RequestBody Mensaje mensaje) {
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
