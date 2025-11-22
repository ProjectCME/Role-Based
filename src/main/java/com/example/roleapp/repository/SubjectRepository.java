package com.example.roleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roleapp.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {}