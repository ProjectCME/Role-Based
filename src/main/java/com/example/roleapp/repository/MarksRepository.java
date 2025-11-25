package com.example.roleapp.repository;

import java.util.List;
import com.example.roleapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.roleapp.model.Subject;
import java.util.Optional;
import com.example.roleapp.model.Marks;

public interface MarksRepository extends JpaRepository<Marks,Long>{
    List<Marks> findByStudentUniqueIdAndSubjectAcademicYearAndSubjectSemester(
            Integer studentId,
            Integer academicYear,
            Integer semester
    );
    List<Marks> findByTeacher(User teacher); //Fetched Marks assigned by teacher, will be used later in views
    Optional<Marks> findByStudentAndSubjectAndExamType(User student, Subject subject, Marks.ExamType examType); //To avoid duplicate entries
}