package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public ResponseEntity<?> register(String name, String username, String password);
    public ResponseEntity<?> login(String username, String password);
    public List<User> getAllById(Integer Id);
}
