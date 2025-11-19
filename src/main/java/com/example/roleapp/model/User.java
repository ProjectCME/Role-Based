package com.example.roleapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    public enum Role { ADMIN, TEACHER, STUDENT }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isStatus;
}
// Class that represent db tables.