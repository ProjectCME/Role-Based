package com.example.roleapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "marks")
public class Marks {

    public enum ExamType { IA1, IA2, IA3, SPECIAL }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    private Integer marks;

    @ManyToOne
    @JoinColumn(name = "created_by_teacher_id", nullable = false)
    private Teacher teacher;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] marksProofPdf;

    public Subject getSubject() {return subject;}
    public ExamType getExamType(){return examType;}
    public Integer getMarks(){return marks;}

    public void setSubject(Subject subject) {this.subject = subject;}
    public void setExamType(ExamType examType){this.examType=examType;}
    public void setMarks(Integer marks){this.marks=marks;}
}
