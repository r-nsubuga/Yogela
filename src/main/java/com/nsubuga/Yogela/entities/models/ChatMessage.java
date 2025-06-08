package com.nsubuga.Yogela.entities.models;

import com.nsubuga.Yogela.entities.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String senderId;
    private String content;
    private String receiverId;
    private String RoomId;
    private MessageType messageType;
    private String timeStamp;
}
