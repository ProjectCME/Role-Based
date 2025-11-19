package com.example.roleapp.model;
import jakarta.persistence.*;
import com.example.roleapp.model.User;
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}