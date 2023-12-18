package com.example.cnpm.quanlythuchinhatro.dto;


public class UpdateUserRequest {
 private String fullname;
 private String phoneNumber;
 private String bankName;
 private String bankNumber;
 private String avatarUrl;

 // Constructors
 public UpdateUserRequest() {
 }

 public UpdateUserRequest(String fullname, String phoneNumber, String bankName, String bankNumber, String avatarUrl) {
     this.fullname = fullname;
     this.phoneNumber = phoneNumber;
     this.bankName = bankName;
     this.bankNumber = bankNumber;
     this.avatarUrl = avatarUrl;
 }

 // Getters and setters
 public String getFullname() {
     return fullname;
 }

 public void setFullname(String fullname) {
     this.fullname = fullname;
 }

 public String getPhoneNumber() {
     return phoneNumber;
 }

 public void setPhoneNumber(String phoneNumber) {
     this.phoneNumber = phoneNumber;
 }

 public String getBankName() {
     return bankName;
 }

 public void setBankName(String bankName) {
     this.bankName = bankName;
 }

 public String getBankNumber() {
     return bankNumber;
 }

 public void setBankNumber(String bankNumber) {
     this.bankNumber = bankNumber;
 }

 public String getAvatarUrl() {
     return avatarUrl;
 }

 public void setAvatarUrl(String avatarUrl) {
     this.avatarUrl = avatarUrl;
 }
}

