// This DTO (Data Transfer Object) class is used to transfer user data and it is used to encapsulate user information.

package com.example.roleapp.dto;

import com.example.roleapp.model.User;

public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String role;
    private boolean status;

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, User.Role role, boolean status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role.name(); // Convert ENUM → String
        this.status = status;
    }

    //getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean isStatus() {
        return status;
    }

  //setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
