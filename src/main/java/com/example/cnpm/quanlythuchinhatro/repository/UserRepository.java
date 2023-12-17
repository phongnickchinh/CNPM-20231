package com.example.cnpm.quanlythuchinhatro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cnpm.quanlythuchinhatro.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    
    User save(User user);
}
