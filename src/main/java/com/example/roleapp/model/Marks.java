package com.example.roleapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "marks")
public class Marks {

    public enum ExamType {
        IA1, IA2, IA3, SPECIAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "uniqueId", nullable = false, columnDefinition = "INT")
    private User student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    private Integer marks;

    @ManyToOne
    @JoinColumn(name = "created_by_teacher_id", referencedColumnName = "uniqueId", nullable = false, columnDefinition = "INT")
    private User teacher;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] marksProofPdf;

    // Getters

    public Long getId() {
        return id;
    }

    public User getStudent() {
        return student;
    }

    public Subject getSubject() {
        return subject;
    }

    public ExamType getExamType() {
        return examType;
    }

    public Integer getMarks() {
        return marks;
    }

    public byte[] getMarksProofPdf() {
        return marksProofPdf;
    }

    public User getTeacher() {
        return teacher;
    }

    // Setters

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public void setMarksProofPdf(byte[] marksProofPdf) {
        this.marksProofPdf = marksProofPdf;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}
