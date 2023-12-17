package com.example.cnpm.quanlythuchinhatro.dto;

import com.example.cnpm.quanlythuchinhatro.model.User;

public class UserRequest {

	 private String fullname;
	 private String username;
	 private String password;
	
	 public UserRequest() {
	 }
	
	 public UserRequest(String fullname, String username, String password) {
	     this.fullname = fullname;
	     this.username = username;
	     this.password = password;
	 }
	
	 // Getters and Setters
	 public String getFullname() {
	     return fullname;
	 }
	
	 public void setFullname(String fullname) {
	     this.fullname = fullname;
	 }
	
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
	
	 // Phương thức chuyển đổi sang User
	 public User toUser() {
	     User user = new User();
	     user.setName(this.fullname);
	     user.setUsername(this.username);
	     user.setPassword(this.password);
	     return user;
	 }
}
