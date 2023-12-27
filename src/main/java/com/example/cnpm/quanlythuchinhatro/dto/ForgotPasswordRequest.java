package com.example.cnpm.quanlythuchinhatro.dto;

public class ForgotPasswordRequest {
	private String username;
    private String securityAnswer;
    private String newPassword;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public ForgotPasswordRequest(String username, String securityAnswer, String newPassword) {
		super();
		this.username = username;
		this.securityAnswer = securityAnswer;
		this.newPassword = newPassword;
	}
	public ForgotPasswordRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
