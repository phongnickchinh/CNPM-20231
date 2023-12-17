package com.example.cnpm.quanlythuchinhatro.dto;

public class UserLoginRequest {
	 private String username;
	 private String password;
	
	 // Constructors
	 public UserLoginRequest() {
	 }
	
	 public UserLoginRequest(String username, String password) {
	     this.username = username;
	     this.password = password;
	 }
	
	 // Getters and setters
	 public String getUsername() {
	     return username;
	 }
	
	 public void setUsername(String username) {
	     this.username = username;
	 }
	
	 public String getPassword() {
	     return password;
	 }
	
	 public void setPassword(String password) {
	     this.password = password;
	 }
}

