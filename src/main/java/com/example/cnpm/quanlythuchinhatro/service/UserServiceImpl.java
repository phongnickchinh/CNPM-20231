package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> register(String name, String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("EXISTED_USERNAME");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setName(name);
        userRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }
    public ResponseEntity<?> login(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    if(passwordEncoder.matches(password, user.getPassword())) {
                        return ResponseEntity.ok().build(); // Login thành công
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("WRONG_PASSWORD"); // Sai mật khẩu
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("USERNAME_DOESNOT_EXIST")); // Username không tồn tại
    }

    @Override
    public List<User> getAllById(Integer Id) {
        return userRepository.getAllById(Id);
    }
}
