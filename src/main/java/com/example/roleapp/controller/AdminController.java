package com.example.roleapp.controller;

import com.example.roleapp.dto.AdminMarkViewDto;
import com.example.roleapp.dto.AdminTeacherSubjectDto;

import java.util.List;
import com.example.roleapp.service.AdminStudentMarksService;
import com.example.roleapp.service.AdminTeacherService;
//import com.example.roleapp.dto.UserDto;
import com.example.roleapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final AdminStudentMarksService adminStudentMarksService;

    private final AdminTeacherService adminTeacherService;

    public AdminController(UserService userService,
            AdminStudentMarksService adminStudentMarksService,
            AdminTeacherService adminTeacherService) {
        this.userService = userService;
        this.adminStudentMarksService = adminStudentMarksService;
        this.adminTeacherService = adminTeacherService;
    }

    // Admin Dashboard Page UI
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboardAdmin"; // loads dashboard-Admin.html
    }

    // User Approval Page UI
    @GetMapping("/user-approval")
    public String userApprovalPage(Model model) {
        model.addAttribute("users", userService.getPendingUsers());
        return "admin/user-approval"; // loads user-approval.html
    }

    // View Users Page UI
    @GetMapping("/view-users")
    public String viewUsersPage(Model model) {
        model.addAttribute("users", userService.getAllActiveUsers());
        return "admin/view-users"; // loads view-users.html
    }

    // Approve user
    @PostMapping("/approve/{id}")
    public String approveUser(@PathVariable Long id) {
        userService.approveUser(id);
        return "redirect:/admin/user-approval"; // reload page
    }

    // Soft-delete user
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return "redirect:/admin/user-approval"; // reload page
    }

    @GetMapping("/student-marks")
    public String viewAllStudentMarks(
            @RequestParam(value = "adminAccess", required = false) String adminAccess,
            Model model) {

        List<AdminMarkViewDto> allMarks = adminStudentMarksService.getAllMarksForAdmin();
        model.addAttribute("marksList", allMarks);

        if (adminAccess != null) {
            model.addAttribute("adminMessage",
                    "You are an Admin and tried to access a student page. Showing full student marks.");
        }

        return "admin/student-marks"; // admin student marks view
    }

    @GetMapping("/teacher-overview")
    public String teacherOverview(
            @RequestParam(value = "adminAccess", required = false) String adminAccess,
            Model model) {

        List<AdminTeacherSubjectDto> list = adminTeacherService.getAllTeacherSubjectMappings();
        model.addAttribute("teacherMappingList", list);

        if (adminAccess != null) {
            model.addAttribute("adminMessage",
                    "You are an Admin and tried to access a teacher page. Showing teacher subject mapping.");
        }

        return "admin/teacher-overview";
    }

}
