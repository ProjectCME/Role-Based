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


    //Getters
    public Long getId() {return id;}
    public String getSubjectName(){return subjectName;}
    public Integer getSemester(){return semester;}
    public Integer getAcademicYear(){return academicYear;}


    //Setters
    public void setId(Long id){this.id=id;}
    public void setSubjectName(String subjectName){this.subjectName=subjectName;}
    public void setSemester(Integer semester){this.semester=semester;}
    public void setAcademicYear(Integer academicYear){this.academicYear=academicYear;}


}

