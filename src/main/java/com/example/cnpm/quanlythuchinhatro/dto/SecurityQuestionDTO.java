package com.example.cnpm.quanlythuchinhatro.dto;

public class SecurityQuestionDTO {
    private String question;
    private String oldAnswer; // Đáp án trước đó
    private String newAnswer; // Đáp án mới

    public SecurityQuestionDTO( String question, String oldAnswer, String newAnswer) {
        this.question = question;
        this.oldAnswer = oldAnswer;
        this.newAnswer = newAnswer;
    }

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOldAnswer() {
		return oldAnswer;
	}

	public void setOldAnswer(String oldAnswer) {
		this.oldAnswer = oldAnswer;
	}

	public String getNewAnswer() {
		return newAnswer;
	}

	public void setNewAnswer(String newAnswer) {
		this.newAnswer = newAnswer;
	}


	
}
