package com.example.biskit.entities.DTOs.Chat.ParticipanteChatDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteChatDTO {

    private Long id;
    private String rol;
}
