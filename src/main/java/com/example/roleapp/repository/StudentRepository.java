package com.example.roleapp.repository;

import com.example.roleapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.roleapp.model.User;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findByUser(User user);
}
