package com.example.cnpm.quanlythuchinhatro.dto;

public class SecurityQuestionRequest {
    private String question;
    private String answer;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public SecurityQuestionRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SecurityQuestionRequest(String question, String answer) {
		super();
		this.question = question;
		this.answer = answer;
	}
    
}