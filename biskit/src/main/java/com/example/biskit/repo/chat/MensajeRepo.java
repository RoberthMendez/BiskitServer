package com.example.biskit.repo.chat;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.biskit.entities.Chat.Mensaje;

@Repository
public interface MensajeRepo extends JpaRepository<Mensaje, Long> {
    
}
