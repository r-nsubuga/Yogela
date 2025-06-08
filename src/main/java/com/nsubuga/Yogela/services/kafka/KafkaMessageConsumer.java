package com.nsubuga.Yogela.services.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsubuga.Yogela.entities.models.ChatMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public KafkaMessageConsumer(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @KafkaListener(topics = "user-*", groupId = "chat-group")
    public void listenPrivateMessage(String message) throws JsonProcessingException {
        ChatMessage chatMessage = new ObjectMapper().readValue(message, ChatMessage.class);
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiverId(), "/queue/messages", chatMessage);
    }

    @KafkaListener(topics = "group-*", groupId = "chat-group")
    public void listenGroupMessages(String message) throws JsonProcessingException {
        ChatMessage chatMessage = new ObjectMapper().readValue(message, ChatMessage.class);
        simpMessagingTemplate.convertAndSend("topic/chatroom" + chatMessage.getRoomId(), chatMessage);
    }

    @KafkaListener(topics="typing-*", groupId = "chat-group")
    public void listenTyping(String message) throws JsonProcessingException {
        ChatMessage typing = new ObjectMapper().readValue(message, ChatMessage.class);
        simpMessagingTemplate.convertAndSendToUser(typing.getReceiverId(), "/queue/typing", typing);
    }

    @KafkaListener(topics="user-notifications-*", groupId = "chat-group")
    public void listenNotifications(String message) throws JsonProcessingException {
        ChatMessage notification = new ObjectMapper().readValue(message, ChatMessage.class);
        simpMessagingTemplate.convertAndSendToUser(notification.getReceiverId(), "/queue/notifications", message);
    }
}
