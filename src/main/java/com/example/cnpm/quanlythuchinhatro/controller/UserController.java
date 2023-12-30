package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.model.AuthRequest;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.service.JwtService;
import com.example.cnpm.quanlythuchinhatro.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody User user) {
//        ResponseEntity<?> responseEntity = userService.register(user.getName(), user.getUsername(), user.getPassword());
//        if (responseEntity.getStatusCode().isError()) {
//            // Nếu có lỗi (ví dụ, tên đăng nhập đã tồn tại), trả về thông báo lỗi
//            return responseEntity;
//        }
//        // Nếu đăng ký thành công, trả về thông tin người dùng mới với status 200 OK
//        return ResponseEntity.ok(responseEntity.getBody());
//    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
//        String username = credentials.get("username");
//        String password = credentials.get("password");
//        return userService.login(username, password);
//    }
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request) {
//        // Trong thực tế, bạn sẽ xóa JWT hoặc xóa cookie, hoặc làm hết hiệu lực phiên hiện tại tùy thuộc vào cách bạn quản lý xác thực.
//        // Ví dụ dưới đây giả định rằng bạn đang xử lý phiên người dùng:
//        // request.getSession().invalidate();
//        return ResponseEntity.ok("Logout successful");
//    }
//    @GetMapping("/get/{Id}")
//    public List<User> getAllById(@PathVariable Integer Id) {
//        return userService.getAllById(Id);
//    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }
    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody User userInfo) {
        return userService.addUser(userInfo);
    }
    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }
    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getUsername());
            } else {
                throw new UsernameNotFoundException("invalid user request !");
            }
        } catch (Throwable t) {
            System.out.println(t);
        }
        return "";
    }
}
