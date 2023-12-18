package com.example.cnpm.quanlythuchinhatro.dto;


public class UserLogoutRequest {
 private String username;

 // Constructors
 public UserLogoutRequest() {
 }

 public UserLogoutRequest(String username) {
     this.username = username;
 }

 // Getters and setters
 public String getUsername() {
     return username;
 }

 public void setUsername(String username) {
     this.username = username;
 }
}
