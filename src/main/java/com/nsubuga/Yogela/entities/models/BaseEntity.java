package com.nsubuga.Yogela.entities.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public class BaseEntity {
    private long id;
    private User createdBy;
    private User changedBy;
    private Date dateCreated;

    private Date dateChanged;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId(){
        return this.id;
    }
}
