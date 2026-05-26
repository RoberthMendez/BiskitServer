package com.example.biskit.repo.chat;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.biskit.entities.Chat.Chat;
import com.example.biskit.entities.Chat.ParticipanteChat;

@Repository
public interface ParticipanteChatRepo extends JpaRepository<ParticipanteChat, Long> {

    Optional<ParticipanteChat> findByCredencialesId(Long senderId);

}
