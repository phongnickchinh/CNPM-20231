package com.example.cnpm.quanlythuchinhatro.dto;

public class SecurityQuestionRequest {
    private String question;
    private String answer;
    private String password;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public SecurityQuestionRequest(String question, String answer, String password) {
		super();
		this.question = question;
		this.answer = answer;
		this.password = password;
	}
	public SecurityQuestionRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}