package com.example.roleapp.model;
import jakarta.persistence.*;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectName;

    private Integer semester;
}

