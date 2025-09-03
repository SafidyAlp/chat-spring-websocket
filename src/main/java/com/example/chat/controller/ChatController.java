package com.example.chat.controller;

import com.example.chat.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

	// Stockage des messages en mémoire
	private final List<Message> messages = new ArrayList<>();

	@MessageMapping("/chat.send")
	@SendTo("/topic/messages")
	public Message sendMessage(Message message) {
		message.setTimestamp(LocalDateTime.now());
		messages.add(message);
		return message;
	}

	// Optionnel : récupérer tous les messages existants au démarrage de la page
	@GetMapping("/messages")
	public List<Message> getMessages() {
		return messages;
	}

	@GetMapping("/")
	public String index() {
		return "chat"; // ton fichier chat.html dans templates
	}
}
