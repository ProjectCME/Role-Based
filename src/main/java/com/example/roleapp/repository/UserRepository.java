// Interfaces file to access db using spring boot.
package com.example.roleapp.repository;

import com.example.roleapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Returns only users with isStatus == true (active users)
    List<User> findByIsStatusTrue();

    // Returns only users with isStatus == false (inactive users)
    List<User> findByIsStatusFalse();
}
