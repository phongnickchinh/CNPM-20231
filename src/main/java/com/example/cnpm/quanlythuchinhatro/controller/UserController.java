package com.example.cnpm.quanlythuchinhatro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cnpm.quanlythuchinhatro.dto.UserLoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserRequest;
import com.example.cnpm.quanlythuchinhatro.service.UserSevice;

@RestController
@RequestMapping("/")
public class UserController {

	 @Autowired
	 private UserSevice userService;
	
	 @PostMapping("signup")
	 public ResponseEntity<?> signup(@RequestBody UserRequest userRequest) {
	     return userService.registerUser(userRequest);
	 }
	 @PostMapping("login")
	    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) {
	        String result = userService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword());

	        if ("Login successful".equals(result)) {
	            return new ResponseEntity<>(result, HttpStatus.OK);
	        } else if ("USERNAME_DOESNOT_EXIST".equals(result)) {
	            return new ResponseEntity<>("USERNAME_DOESNOT_EXIST", HttpStatus.UNAUTHORIZED);
	        } else {
	            return new ResponseEntity<>("WRONG_PASSWORD", HttpStatus.UNAUTHORIZED);
	        }
	    }
}

