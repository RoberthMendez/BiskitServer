package com.example.biskit.repo.chat;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.biskit.entities.Chat.Chat;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {
    
}
