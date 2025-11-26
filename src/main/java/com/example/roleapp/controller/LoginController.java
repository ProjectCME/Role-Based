package com.example.roleapp.controller;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.roleapp.dto.RegistrationDto;
import com.example.roleapp.model.OtpEvent;
import com.example.roleapp.model.User;
import com.example.roleapp.repository.UserRepository;
import com.example.roleapp.service.EmailOtpService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final EmailOtpService emailOtpService;
    private final UserRepository userRepository;

    public LoginController(EmailOtpService emailOtpService, UserRepository userRepository) {
        this.emailOtpService = emailOtpService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/send-login-otp")
    @ResponseBody
    public String sendLoginOtp(@RequestParam String email, Model model) {
        try {
            emailOtpService.sendOtp(email, OtpEvent.Purpose.LOGIN);
            model.addAttribute("success", "OTP sent to your email.");
            return "OTP sent to your email.";
        } catch (Exception e) {
            return "Error sending OTP: " + e.getMessage();
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String otp, Model model,
            HttpServletRequest request) {
        boolean verified = emailOtpService.verifyOtp(username, otp, OtpEvent.Purpose.LOGIN);
        if (!verified) {
            model.addAttribute("error", "Invalid OTP or expired.");
            return "login";
        }
        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found.");
            return "login";
        }
        User user = userOpt.get();

        // Check approval status
        if (!user.isStatus()) {
            model.addAttribute("error", "Your account is not approved by admin yet.");
            return "login";
        }

        // save auth in session
        request.getSession().setAttribute("userRole", user.getRole().name());
        request.getSession().setAttribute("userEmail", user.getEmail());
        request.getSession().setAttribute("studentUniqueId", user.getUniqueId());
        // Redirect based on role
        switch (user.getRole()) {
            case ADMIN:
                return "redirect:/admin/dashboard";
            case TEACHER:
                return "redirect:/teacher/dashboard";
            case STUDENT:
                return "redirect:/student/dashboard";
            default:
                model.addAttribute("error", "Invalid role.");
                return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new RegistrationDto());
        model.addAttribute("roles", Arrays.asList("TEACHER", "STUDENT"));
        return "register";
    }

    @PostMapping("/send-reg-otp")
    @ResponseBody
    public String sendRegOtp(@RequestParam String name, @RequestParam String email, @RequestParam String role) {
        try {
            // Create user if not exists
            Optional<User> existing = userRepository.findByEmail(email);
            if (existing.isPresent()) {
                return "User already exists.";
            }
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setRole(User.Role.valueOf(role));
            user.setStatus(false); // pending
            // uniqueId ? perhaps generate
            user.setUniqueId((int) (Math.random() * 100000)); // placeholder
            userRepository.save(user);
            emailOtpService.sendOtp(email, OtpEvent.Purpose.REGISTRATION);
            return "OTP sent to your email.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationDto dto, Model model) {
        try {
            boolean verified = emailOtpService.verifyOtp(dto.getEmail(), dto.getOtp(), OtpEvent.Purpose.REGISTRATION);
            if (verified) {
                // User is already created and status set to true in verifyOtp
                model.addAttribute("success", "Registration successful. Please wait for admin approval.");
                return "login";
            } else {
                model.addAttribute("error", "Invalid OTP.");
                model.addAttribute("user", dto); // but template expects user, perhaps change to registrationDto
                model.addAttribute("roles", Arrays.asList("TEACHER", "STUDENT"));
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            model.addAttribute("user", dto);
            model.addAttribute("roles", Arrays.asList("TEACHER", "STUDENT"));
            return "register";
        }
    }
}