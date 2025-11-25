package com.example.roleapp.service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.roleapp.model.OtpEvent;
import com.example.roleapp.model.User;
import com.example.roleapp.repository.OtpEventRepository;
import com.example.roleapp.repository.UserRepository;

@Service
public class EmailOtpService {

    private final JavaMailSender mailSender;
    private final OtpEventRepository otpEventRepository;
    private final UserRepository userRepository;

    public EmailOtpService(JavaMailSender mailSender, OtpEventRepository otpEventRepository,
            UserRepository userRepository) {
        this.mailSender = mailSender;
        this.otpEventRepository = otpEventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void sendOtp(String email, OtpEvent.Purpose purpose) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        User user = userOpt.get();

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Create OtpEvent
        OtpEvent otpEvent = new OtpEvent();
        otpEvent.setUser(user);
        otpEvent.setOtpCode(otp);
        otpEvent.setPurpose(purpose);
        otpEvent.setStatus(OtpEvent.Status.PENDING);
        otpEvent.setCreatedAt(Instant.now().toEpochMilli());
        otpEvent.setExpiryTime(Instant.now().toEpochMilli() + 5 * 60 * 1000); // 5 minutes

        otpEventRepository.save(otpEvent);

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + ". It expires in 5 minutes.");
        mailSender.send(message);
    }

    @Transactional
    public boolean verifyOtp(String email, String otp, OtpEvent.Purpose purpose) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();

        Optional<OtpEvent> otpEventOpt = otpEventRepository.findTopByUserAndPurposeAndStatusOrderByCreatedAtDesc(user,
                purpose, OtpEvent.Status.PENDING);
        if (otpEventOpt.isEmpty()) {
            return false;
        }
        OtpEvent otpEvent = otpEventOpt.get();

        if (!otpEvent.getOtpCode().equals(otp) || Instant.now().toEpochMilli() > otpEvent.getExpiryTime()) {
            return false;
        }

        // Mark as verified
        otpEvent.setStatus(OtpEvent.Status.VERIFIED);
        otpEventRepository.save(otpEvent);

        return true;
    }
}