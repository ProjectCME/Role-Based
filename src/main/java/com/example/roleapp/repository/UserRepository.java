// Interfaces file to access db using spring boot.
package com.example.roleapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roleapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Returns only users with isStatus == true (active users)
    List<User> findByIsStatusTrue();

    // Returns only users with isStatus == false (inactive users)
    List<User> findByIsStatusFalse();

    User findByUniqueId(Integer uniqueId);  // uniqueId is an Integer field in User model not an string

    Optional<User> findByEmail(String email);
}
