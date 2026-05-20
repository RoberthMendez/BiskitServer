package com.example.biskit.repo.chat;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.biskit.entities.Chat.ParticipanteChat;

@Repository
public interface ParticipanteChatRepo extends JpaRepository<ParticipanteChat, Long> {
    
}
