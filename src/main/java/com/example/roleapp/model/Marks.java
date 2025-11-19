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
}
