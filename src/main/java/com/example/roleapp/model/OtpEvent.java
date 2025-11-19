package com.example.roleapp.model;
import com.example.demo.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "otp_events")
public class OtpEvent {

    public enum Purpose { REGISTRATION, LOGIN, PASSWORD_RESET }
    public enum Status { PENDING, VERIFIED, EXPIRED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String otpCode;

    @Enumerated(EnumType.STRING)
    private Purpose purpose;

    private Long expiryTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long createdAt;
}
