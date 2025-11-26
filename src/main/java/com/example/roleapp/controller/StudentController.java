package com.example.roleapp.controller;

import com.example.roleapp.dto.MarkDto;
import com.example.roleapp.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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

    @GetMapping("/dashboard")
    public String studentHome(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }

        model.addAttribute("studentUniqueId", session.getAttribute("studentUniqueId"));
        return "student/student";
    }

    @GetMapping("/grades")
    public String getGrades(
            @RequestParam Integer studentId,
            @RequestParam Integer year,
            @RequestParam Integer semester,
            HttpServletRequest request,
            Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }

        Integer loggedInStudentId = (Integer) session.getAttribute("studentUniqueId");

        // student cannot view others' marks
        if (!loggedInStudentId.equals(studentId)) {
            return "redirect:/403";
        }
        List<MarkDto> grades = studentService.getGrades(studentId, year, semester);

        if (grades.isEmpty()) {
            model.addAttribute("message", "No marks uploaded yet for this year/semester");
            return "student/grades"; // this is grades.html
        }

        model.addAttribute("grades", grades);
        model.addAttribute("studentId", studentId);
        model.addAttribute("year", year);
        model.addAttribute("semester", semester);

        return "student/grades"; // Thymeleaf template
    }
}
