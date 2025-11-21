package com.example.roleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.roleapp.model.*;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks,Long> {
    //fetch marks for student by semester and academic year
    List<Marks> findByStudentAndSubject(Student student,Integer academicYear,Integer semester);

    //check if it marks exists for the student by semester and academic year
    boolean existsByStudentAndSubject(Student student,Integer academicYear,Integer semester);
}