package com.example.biskit.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/whatsapp")
public class WhatsAppWebhookController {

  @Value("${whatsapp.verify-token}")
  private String verifyToken;

  // Meta llama este GET para verificar que el servidor es tuyo
  @GetMapping
  public ResponseEntity<String> verify(
    @RequestParam("hub.mode") String mode,
    @RequestParam("hub.verify_token") String token,
    @RequestParam("hub.challenge") String challenge
  ) {
    if ("subscribe".equals(mode) && verifyToken.equals(token)) {
      return ResponseEntity.ok(challenge);
    }
    return ResponseEntity.status(403).build();
  }

  // Meta llamará este POST cada vez que el cliente mande un mensaje
  @PostMapping
  public ResponseEntity<String> receive(@RequestBody String payload) {
    System.out.println("Mensaje recibido: " + payload);
    return ResponseEntity.ok("EVENT_RECEIVED");
  }
}
