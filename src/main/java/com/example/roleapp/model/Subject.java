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
    
    private Integer academicYear;

    public Long getId() {return id;}
    public String getSubjectName(){return subjectName;}
    public Integer getSemester(){return semester;}
    public Integer getAcademicYear(){return academicYear;}



}

