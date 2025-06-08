package com.nsubuga.Yogela.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsubuga.Yogela.entities.enums.MessageType;
import com.nsubuga.Yogela.entities.models.ChatMessage;
import com.nsubuga.Yogela.services.kafka.KafkaMessageProducer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {
    private final KafkaMessageProducer kafkaMessageProducer;

    public ChatController(KafkaMessageProducer kafkaMessageProducer){
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    @MessageMapping("/chat.send")
    public void processMessage(@Payload ChatMessage chatMessage) throws JsonProcessingException {
        String topic = chatMessage.getRoomId() != null ? "group-" + chatMessage.getRoomId()
                : "user-" + chatMessage.getReceiverId();
        String message = new ObjectMapper().writeValueAsString(chatMessage);
        this.kafkaMessageProducer.sendMessage(topic, message);
    }

    @MessageMapping("/chat.typing")
    public void typingIndicator(@Payload ChatMessage typingMessage) throws JsonProcessingException {
        typingMessage.setMessageType(MessageType.TYPING);
        this.kafkaMessageProducer.sendMessage("typing-" + typingMessage.getReceiverId(),
                new ObjectMapper().writeValueAsString(typingMessage));
    }
}
