package com.example.roleapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roleapp.model.Marks;

public interface MarksRepository extends JpaRepository<Marks,Long>{
    List<Marks> findByStudentUniqueIdAndSubjectAcademicYearAndSubjectSemester(
            String studentId,
            Integer academicYear,
            Integer semester
    );
}