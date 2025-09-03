package com.example.chat.service;

import com.example.chat.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MessageService {
    private final List<Message> messages = new CopyOnWriteArrayList<>();

    public List<Message> getAllMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }
}
