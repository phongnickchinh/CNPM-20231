package com.example.cnpm.quanlythuchinhatro.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
	private String username;
	 private String fullname;
	 private String phoneNumber;
	 private String bankName;
	 private String bankNumber;
	 private String avatarUrl;
	 private Integer UserId;

 // Constructors
 public UpdateUserRequest() {
 }

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

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

