// business logic will be here
package com.example.roleapp.service;

import com.example.roleapp.dto.UserDto;
import com.example.roleapp.exception.ResourceNotFoundException;
import com.example.roleapp.model.User;
import com.example.roleapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Return list of active users as DTOs
    @Transactional(readOnly = true)
    public List<UserDto> getAllActiveUsers() {
        List<User> users = userRepository.findByIsStatusTrue();
        return users.stream()
                .map(u -> new UserDto(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.isStatus()))
                .collect(Collectors.toList());
    }

    // Approve user (set isStatus = true)
    @Transactional
    public void approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setStatus(true); 
        userRepository.save(user);
    }

    // Soft delete user (set isStatus = false)
    @Transactional
    public void softDeleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setStatus(false);
        userRepository.save(user);
    }

    // Return list of pending users (inactive users)
@Transactional(readOnly = true)
public List<UserDto> getPendingUsers() {
    List<User> users = userRepository.findByIsStatusFalse();
    return users.stream()
            .map(u -> new UserDto(
                    u.getId(),
                    u.getName(),
                    u.getEmail(),
                    u.getRole(),
                    u.isStatus()
            ))
            .collect(Collectors.toList());
}

}

