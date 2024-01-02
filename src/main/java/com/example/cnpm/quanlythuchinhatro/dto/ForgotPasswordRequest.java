package com.example.cnpm.quanlythuchinhatro.dto;

public class ForgotPasswordRequest {
    private String username;
    private String answer;

    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String username, String answer) {
        this.username = username;
        this.answer = answer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

