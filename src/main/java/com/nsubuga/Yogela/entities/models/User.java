package com.nsubuga.Yogela.entities.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;


}
