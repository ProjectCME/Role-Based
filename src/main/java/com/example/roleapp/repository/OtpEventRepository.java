package com.example.roleapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roleapp.model.OtpEvent;
import com.example.roleapp.model.User;

@Repository
public interface OtpEventRepository extends JpaRepository<OtpEvent, Long> {

    Optional<OtpEvent> findTopByUserAndPurposeAndStatusOrderByCreatedAtDesc(User user, OtpEvent.Purpose purpose, OtpEvent.Status status);

    Optional<OtpEvent> findByOtpCodeAndPurposeAndStatus(String otpCode, OtpEvent.Purpose purpose, OtpEvent.Status status);
}