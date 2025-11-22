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

    public Long getId() {
        return id;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    public Integer getSemester() {
        return semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    //Setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}

