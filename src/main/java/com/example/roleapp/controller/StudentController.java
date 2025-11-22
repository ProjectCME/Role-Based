package com.example.roleapp.controller;

import com.example.roleapp.dto.MarkDto;
import com.example.roleapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/grades")
    public String getGrades(
            @RequestParam Integer studentId,
            @RequestParam Integer year,
            @RequestParam Integer semester,
            Model model) {

        List<MarkDto> grades = studentService.getGrades(studentId, year, semester);

        if (grades.isEmpty()) {
            model.addAttribute("message", "No marks uploaded yet for this year/semester");
            return "grades";   // this is grades.html
        }

        model.addAttribute("grades", grades);
        model.addAttribute("studentId", studentId);
        model.addAttribute("year", year);
        model.addAttribute("semester", semester);

        return "grades";   // Thymeleaf template
    }
}
