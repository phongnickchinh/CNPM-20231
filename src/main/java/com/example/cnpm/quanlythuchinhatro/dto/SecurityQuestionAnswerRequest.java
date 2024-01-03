package com.example.cnpm.quanlythuchinhatro.dto;

public class SecurityQuestionAnswerRequest {

    private Integer questionId;
    private String answer;
    private String newPassword;
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public SecurityQuestionAnswerRequest(Integer questionId, String answer, String newPassword) {
		super();
		this.questionId = questionId;
		this.answer = answer;
		this.newPassword = newPassword;
	}
	public SecurityQuestionAnswerRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}

