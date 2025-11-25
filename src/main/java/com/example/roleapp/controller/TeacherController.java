package com.example.roleapp.controller;

import com.example.roleapp.model.Subject;
import com.example.roleapp.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // Helper method to populate the subject dropdown for every request
    private void loadSubjects(Model model, Principal principal) {
        if (principal != null) {
            // principal.getName() returns the teacher's Unique ID as a String
            List<Subject> subjects = teacherService.getAssignedSubjects(principal.getName());
            model.addAttribute("teacherSubjects", subjects);
        }
    }

    // 1. Dashboard Page Load
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        loadSubjects(model, principal);
        return "teacher/dashboardTeacher";
    }

    // 2. Handle CSV Upload
    @PostMapping("/upload-marks")
    public String uploadMarks(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("examType") String examType,
            Model model, Principal principal) {
        
        try {
            teacherService.saveMarks(file, subjectId, examType, principal.getName());
            model.addAttribute("message", "Marks uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error uploading file: " + e.getMessage());
        }
        
        loadSubjects(model, principal); // Ensure dropdowns stay populated
        return "teacher/dashboardTeacher";
    }

    // 3. Handle View/Search Marks
    @GetMapping("/view-marks")
    public String viewMarks(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer semester,
            @RequestParam(required = false) Long subjectId,
            Model model, Principal principal) {

        if (principal != null) {
            model.addAttribute("studentList", 
                teacherService.getTeacherViewData(principal.getName(), year, semester, subjectId));
        }
        
        loadSubjects(model, principal); // Ensure dropdowns stay populated
        return "teacher/dashboardTeacher";
    }
}