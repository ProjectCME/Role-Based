package com.example.roleapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.roleapp.dto.MarkDto;
import com.example.roleapp.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Example: /student/grades?year=1&semester=2
    @GetMapping("/grades")
    public ResponseEntity<?> getGrades(
            @RequestParam String studentId,
            @RequestParam Integer year,
            @RequestParam Integer semester) {

        List<MarkDto> grades =
                studentService.getGrades(studentId, year, semester);

        if (grades.isEmpty()) {
            return ResponseEntity.ok("No marks uploaded yet for this year/semester");
        }
        return ResponseEntity.ok(grades);
    }
}
