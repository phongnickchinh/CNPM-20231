package com.example.cnpm.quanlythuchinhatro.dto;

public class SecurityQuestionDTO {
    private String question;

    public SecurityQuestionDTO(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}