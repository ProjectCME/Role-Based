package com.example.roleapp.repository;

import com.example.roleapp.model.TeacherSubject;
import com.example.roleapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject, Long> {
    //subject mappings for a specific teacher
    List<TeacherSubject> findByTeacher(User teacher);
}