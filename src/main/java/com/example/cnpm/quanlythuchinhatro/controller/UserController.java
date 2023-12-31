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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cnpm.quanlythuchinhatro.dto.ChangePasswordRequest;
import com.example.cnpm.quanlythuchinhatro.dto.ForgotPasswordRequest;
import com.example.cnpm.quanlythuchinhatro.dto.LoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserSignUpRequest;
import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.service.SecurityQuestionService;
import com.example.cnpm.quanlythuchinhatro.service.UserSevice;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("")
public class UserController {

	 @Autowired
	 private UserSevice userService;
	 
	 @Autowired
	 private SecurityQuestionService securityQuestionService;
	 

	 @PostMapping("/signup")
	    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
	        return userService.signUp(userSignUpRequest);
	    }
	 	 @PutMapping("/update")
	    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
	        return userService.updateUser(updateUserRequest);
	    }
	    
	 	@PutMapping("/change-password")
	    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
	        return userService.changePassword(changePasswordRequest);
	    }

	 	@PostMapping("/forgot-password")
	    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
	        return userService.forgotPassword(request);
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

	    @GetMapping("/logout")
	    public String logout(HttpSession session) {
	        // Xóa thông tin người dùng khỏi session khi đăng xuất
	        session.removeAttribute("loggedInUser");
	        return "login";
	    }

	    
	     
	     @GetMapping("/signup")
	     public String getSignupPage(Model model) {
	         model.addAttribute("signupRequest", new UserSignUpRequest());
	         return "signup";
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


