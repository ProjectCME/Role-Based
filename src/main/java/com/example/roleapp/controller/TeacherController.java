package com.example.roleapp.controller;

import com.example.roleapp.model.Subject;
import com.example.roleapp.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //Check Admin Session ROle
    private boolean isAdmin(HttpServletRequest request) {
        if (request == null)
            return false;
        if (request.getSession(false) == null)
            return false;
        Object roleObj = request.getSession(false).getAttribute("userRole");
        return roleObj != null && "ADMIN".equals(roleObj.toString());
    }

    //Taking Teacher ID as string from principal/session
    private String getTeacherId(Principal principal, HttpServletRequest request) {
        //Try Principal 
        if (principal != null) {
            return principal.getName(); 
        }
        //Try Session
        HttpSession session = request.getSession(false);
        if (session != null) {
            //Retrieve ID
            Object idObj = session.getAttribute("studentUniqueId"); 
            if (idObj != null) {
                return String.valueOf(idObj);
            }
        }
        return null;
    }

    private void loadSubjects(Model model, String teacherId) {
        List<Subject> subjects = Collections.emptyList();
        try {
            if (teacherId != null) {
                subjects = teacherService.getAssignedSubjects(teacherId);
            }
        } catch (Exception ignored) {
            subjects = Collections.emptyList();
        }
        model.addAttribute("teacherSubjects", subjects);
    }

    //Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal, HttpServletRequest request) {
        if (isAdmin(request)) {
            return "redirect:/admin/teacher-overview?adminAccess=true";
        }
        
        String teacherId = getTeacherId(principal, request);
        loadSubjects(model, teacherId);
        return "teacher/dashboardTeacher";
    }

    //CSV Uploads
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

        String teacherId = getTeacherId(principal, request);

        try {
            teacherService.saveMarks(file, subjectId, examType, teacherId);
            model.addAttribute("message", "Marks uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error uploading file: " + e.getMessage());
        }

        loadSubjects(model, teacherId);
        model.addAttribute("activeSection", "upload");
        return "teacher/dashboardTeacher";
    }

    //View/Search Marks
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

        String teacherId = getTeacherId(principal, request);

        try {
            model.addAttribute("studentList",
                    teacherService.getTeacherViewData(teacherId, year, semester, subjectId));
        } catch (Exception e) {
            model.addAttribute("error", "Error loading marks: " + e.getMessage());
        }

        loadSubjects(model, teacherId);
        model.addAttribute("activeSection", "view");
        return "teacher/dashboardTeacher";
    }
}