package com.example.roleapp.model;
import com.example.roleapp.model.User;
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
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private String otpCode;

    @Enumerated(EnumType.STRING)
    private Purpose purpose;

    private Long expiryTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long createdAt;


    //Getters

    public Long getId(){
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getOtpCode(){
        return otpCode;
    }

    public Purpose getPurpose(){
        return purpose;
    }

    public Long getExpiryTime(){
        return expiryTime;
    }

    public Status getStatus(){
        return status;
    }

    public Long getCreatedAt(){
        return createdAt;
    }

    //Setters


    public void setId(Long id) {
        this.id = id;
    }


    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
