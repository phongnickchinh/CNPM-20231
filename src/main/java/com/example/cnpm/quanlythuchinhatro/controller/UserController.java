package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        ResponseEntity<?> responseEntity = userService.register(user.getName(), user.getUsername(), user.getPassword());
        if (responseEntity.getStatusCode().isError()) {
            // Nếu có lỗi (ví dụ, tên đăng nhập đã tồn tại), trả về thông báo lỗi
            return responseEntity;
        }
        // Nếu đăng ký thành công, trả về thông tin người dùng mới với status 200 OK
        return ResponseEntity.ok(responseEntity.getBody());
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        return userService.login(username, password);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Trong thực tế, bạn sẽ xóa JWT hoặc xóa cookie, hoặc làm hết hiệu lực phiên hiện tại tùy thuộc vào cách bạn quản lý xác thực.
        // Ví dụ dưới đây giả định rằng bạn đang xử lý phiên người dùng:
        // request.getSession().invalidate();
        return ResponseEntity.ok("Logout successful");
    }
}
