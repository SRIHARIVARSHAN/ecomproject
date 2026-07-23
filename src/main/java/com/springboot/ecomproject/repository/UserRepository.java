package com.springboot.ecomproject.repository;

import com.springboot.ecomproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            select u from User u where u.username = ?1 AND u.isActivated = true
            """)
    Optional<User> loadUserByUsername(String username);
}
