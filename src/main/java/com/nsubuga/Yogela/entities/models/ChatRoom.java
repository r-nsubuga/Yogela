package com.nsubuga.Yogela.entities.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {
    @Id
    private String id;
    private String name;
    private boolean isGroup;
    @ManyToMany
    private List<User> participants;
}
