package com.example.cnpm.quanlythuchinhatro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cnpm.quanlythuchinhatro.dto.LoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserSignUpRequest;
import com.example.cnpm.quanlythuchinhatro.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("")
public class UserController {

	 @Autowired
	 private UserService userService;

	 @PostMapping("/signup")
	 public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
		 return userService.signUp(userSignUpRequest);
	 }


	 @PostMapping("/login")
	 public ResponseEntity<?> login(@RequestBody LoginRequest userLoginRequest, HttpSession session) {
		 ResponseEntity<?> response = userService.login(userLoginRequest);
	        
		 // Nếu đăng nhập thành công, lưu thông tin người dùng vào session
		 if (response.getStatusCode().equals(HttpStatus.OK)) {
			 session.setAttribute("loggedInUser", userLoginRequest.getUsername());
		 }
		 return response;
	    }
	 @GetMapping("/logout2")
	 public String logout(HttpSession session) {
		 // Xóa thông tin người dùng khỏi session khi đăng xuất
		 session.removeAttribute("loggedInUser");
		 return "login";
	 }

	 @GetMapping("/current-user")
	 public ResponseEntity<?> getCurrentUser(HttpSession session) {
		 Object loggedInUser = session.getAttribute("loggedInUser");
		 if (loggedInUser != null) {
			 return ResponseEntity.ok("Current user: " + loggedInUser.toString());
		 } else {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is logged in");
		 }
	 }
	 @GetMapping("/info")
	 public ResponseEntity<?> getUserInfo(HttpSession session) {
		 // Kiểm tra xem người dùng đã đăng nhập hay chưa
		 Object loggedInUser = session.getAttribute("loggedInUser");
		 if (loggedInUser != null) {
			 // Lấy thông tin người dùng từ service hoặc repository
			 UpdateUserRequest userInfo = userService.getUserInfo(loggedInUser.toString());
			 return ResponseEntity.ok(userInfo);
		 } else {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is logged in");
		 }
	 }

}


