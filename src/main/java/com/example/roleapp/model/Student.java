package com.example.roleapp.model;
import jakarta.persistence.*;
import com.example.roleapp.model.User;
@Entity
@Table(name = "student")

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = true, nullable = false)
    private String rollNo;


    //Getters
    public Long getId(){return id;}
    public User getUser(){return user;}
    public String getRollNo(){return rollNo;}


    //Setters
    public void setId(){this.id=id;}
    public void setUser(){this.user=user;}
    public void setRollNo(){this.rollNo=rollNo;}
}