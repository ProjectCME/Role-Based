package com.example.roleapp.controller;

import com.example.roleapp.model.Subject;
import com.example.roleapp.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // helper method to check admin session role
    private boolean isAdmin(HttpServletRequest request) {
        if (request == null)
            return false;
        if (request.getSession(false) == null)
            return false;
        Object roleObj = request.getSession(false).getAttribute("userRole");
        return roleObj != null && "ADMIN".equals(roleObj.toString());
    }

    private void loadSubjects(Model model, Principal principal, HttpServletRequest request) {
        List<Subject> subjects = Collections.emptyList();

        try {
            if (principal != null) {
                subjects = teacherService.getAssignedSubjects(principal.getName());
            } else if (request != null) {
                Object emailObj = request.getSession(false) != null
                        ? request.getSession(false).getAttribute("userEmail")
                        : null;
                if (emailObj instanceof String) {
                    // service method should resolve subjects by teacher email
                    subjects = teacherService.getAssignedSubjectsByEmail((String) emailObj);
                }
            }
        } catch (Exception ignored) {
            subjects = Collections.emptyList();
        }

        model.addAttribute("teacherSubjects", subjects);
    }

    // 1. Dashboard Page Load
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal, HttpServletRequest request) {
        if (isAdmin(request)) {
            return "redirect:/admin/teacher-overview?adminAccess=true";
        }
        loadSubjects(model, principal, request);
        return "teacher/dashboardTeacher";
    }

    // 2. Handle CSV Upload (marks)
    @PostMapping("/upload-marks")
    public String uploadMarks(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("examType") String examType,
            Model model,
            Principal principal,
            HttpServletRequest request) {
        if (isAdmin(request)) {
            return "redirect:/admin/teacher-overview?adminAccess=true";
        }
        String teacherIdentifier = null;
        if (principal != null)
            teacherIdentifier = principal.getName();
        else if (request.getSession(false) != null)
            teacherIdentifier = (String) request.getSession(false).getAttribute("userEmail");

        try {
            teacherService.saveMarks(file, subjectId, examType, teacherIdentifier);
            model.addAttribute("message", "Marks uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error uploading file: " + e.getMessage());
        }

        loadSubjects(model, principal, request);
        return "teacher/dashboardTeacher";
    }

    // 3. Handle View/Search Marks
    @GetMapping("/view-marks")
    public String viewMarks(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer semester,
            @RequestParam(required = false) Long subjectId,
            Model model,
            Principal principal,
            HttpServletRequest request) {
        if (isAdmin(request)) {
            return "redirect:/admin/teacher-overview?adminAccess=true";
        }
        String teacherIdentifier = null;
        if (principal != null)
            teacherIdentifier = principal.getName();
        else if (request.getSession(false) != null)
            teacherIdentifier = (String) request.getSession(false).getAttribute("userEmail");

        try {
            model.addAttribute("studentList",
                    teacherService.getTeacherViewData(teacherIdentifier, year, semester, subjectId));
        } catch (Exception e) {
            model.addAttribute("error", "Error loading marks: " + e.getMessage());
        }

        loadSubjects(model, principal, request);
        return "teacher/dashboardTeacher";
    }
}