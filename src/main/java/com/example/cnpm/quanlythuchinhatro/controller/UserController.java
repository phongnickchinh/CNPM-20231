package com.example.cnpm.quanlythuchinhatro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserLoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserLogoutRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserRequest;
import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.service.SecurityQuestionService;
import com.example.cnpm.quanlythuchinhatro.service.UserSevice;

@RestController
@RequestMapping("/")
public class UserController {

	 @Autowired
	 private UserSevice userService;
	 
	 @Autowired
	 private SecurityQuestionService securityQuestionService;
	 
	 @GetMapping("dashboard")
	    public String dashboard(@AuthenticationPrincipal User user, Model model) {
	        // Thông tin người dùng được truyền tự động thông qua @AuthenticationPrincipal
	        model.addAttribute("user", user);
	        return "dashboard";
	    }
	 
	 @GetMapping("login")
	 public String getLoginPage() {
	        return "login"; 
	 }
	 
	 @GetMapping("signup")
	 public String getSignup() {
	        return "signup"; 
	 }
	 
	 @PostMapping("save")
	 public String saveUser(@RequestBody UserRequest userRequest) {
		 String id = userService.addUser(userRequest);
		 return id;
	 }
	 @PostMapping("signup")
	 public ResponseEntity<?> signup(@RequestBody UserRequest userRequest) {
		   ResponseEntity<?> registrationResult = userService.registerUser(userRequest);

	        if (registrationResult.getStatusCode() == HttpStatus.OK) {
	            // Registration successful, redirect to the main page
	            return new ResponseEntity<>("redirect:/dashboard", HttpStatus.OK);
	        } else {
	            // Registration failed, return the original result
	            return registrationResult;
	        }
	 }
	 @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) {
	        String username = userLoginRequest.getUsername();
	        String password = userLoginRequest.getPassword();

	        String result = userService.login(username, password);

	        if ("Login successful".equals(result)) {
	            return new ResponseEntity<>("redirect:/dashboard", HttpStatus.OK);
	        } else if ("USERNAME_DOESNOT_EXIST".equals(result)) {
	            return new ResponseEntity<>("USERNAME_DOESNOT_EXIST", HttpStatus.UNAUTHORIZED);
	        } else {
	            return new ResponseEntity<>("WRONG_PASSWORD", HttpStatus.UNAUTHORIZED);
	        }
	    }
	 
	 @PostMapping("logout")
	    public ResponseEntity<String> logout(@RequestBody UserLogoutRequest userLogoutRequest) {
	        userService.logout(userLogoutRequest.getUsername());
	        return new ResponseEntity<>("redirect:/login", HttpStatus.OK);
	    }

	    @PutMapping("user/{username}")
	    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody UpdateUserRequest updateUserRequest) {
	        User updatedUser = userService.updateUser(username, updateUserRequest);

	        if (updatedUser != null) {
	            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    @PostMapping("user/change-password")
	    public ResponseEntity<?> changePassword(@RequestBody UserLoginRequest loginRequest, @RequestParam String newPassword) {
	        return userService.changePassword(loginRequest, newPassword);
	    }
	    
	    @PostMapping("/forgot-password")
	    public ResponseEntity<String> verifySecurityQuestions(
	            @RequestParam String username,
	            @RequestBody List<SecurityQuestion> answers) {
	        if (securityQuestionService.verifySecurityAnswers(username, answers)) {
	            // Câu trả lời đúng, cho phép người dùng đặt lại mật khẩu
	            return new ResponseEntity<>("Security questions verified successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Invalid security answers", HttpStatus.UNAUTHORIZED);
	        }
	    }

	    // Endpoint để đặt lại mật khẩu sau khi kiểm tra câu hỏi bảo mật
	    @PostMapping("/reset-password")
	    public ResponseEntity<String> resetPassword(
	            @RequestParam String username,
	            @RequestParam String newPassword) {
	        return userService.resetPassword(username, newPassword);
	    }
	    
	    public String getCurrentUsername() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	            return userDetails.getUsername();
	        }

	        return "AnonymousUser";
	    }
}

