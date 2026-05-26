package com.example.biskit.controller;

import com.example.biskit.service.ServicioWhatsAppBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/whatsapp")
public class WhatsAppWebhookController {

  @Value("${whatsapp.verify-token}")
  private String verifyToken;

  private final ServicioWhatsAppBot servicioWhatsAppBot;

  public WhatsAppWebhookController(ServicioWhatsAppBot servicioWhatsAppBot) {
    this.servicioWhatsAppBot = servicioWhatsAppBot;
  }

  @GetMapping
  public ResponseEntity<String> verificarWebhook(
    @RequestParam("hub.mode") String mode,
    @RequestParam("hub.verify_token") String token,
    @RequestParam("hub.challenge") String challenge
  ) {
    if ("subscribe".equals(mode) && verifyToken.equals(token)) {
      return ResponseEntity.ok(challenge);
    }
    return ResponseEntity.status(403).build();
  }

  @PostMapping
  public ResponseEntity<String> recibirEvento(@RequestBody String payload) {
    servicioWhatsAppBot.procesarPayloadAsync(payload);
    return ResponseEntity.ok("EVENT_RECEIVED");
  }
}
