package com.dev.demo.user.model;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique=true)
    private String email;
    @Column(unique=true)
    private String phoneNumber;
    private String address;
    private String password;

}