package com.example.roleapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher_subject")

public class TeacherSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;


    public Subject getSubject() {
        return subject;
    }

    public Long getId() {
        return id;
    }

    public User getTeacher() {
        return teacher;
    }

    //Setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


}