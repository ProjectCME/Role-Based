package com.example.roleapp.controller;

//import com.example.roleapp.dto.UserDto;
import com.example.roleapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    //  Admin Dashboard Page UI
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboardAdmin";  // loads dashboard-Admin.html
    }

    //  User Approval Page UI
    @GetMapping("/user-approval")
    public String userApprovalPage(Model model) {
        model.addAttribute("users", userService.getPendingUsers());
        return "admin/user-approval"; // loads user-approval.html
    }

    //  View Users Page UI
    @GetMapping("/view-users")
    public String viewUsersPage(Model model) {
        model.addAttribute("users", userService.getAllActiveUsers());
        return "admin/view-users"; // loads view-users.html
    }

    //  Approve user
    @PostMapping("/approve/{id}")
    public String approveUser(@PathVariable Long id) {
        userService.approveUser(id);
        return "redirect:/admin/user-approval"; // reload page
    }

    //  Soft-delete user
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return "redirect:/admin/user-approval"; // reload page
    }
}
