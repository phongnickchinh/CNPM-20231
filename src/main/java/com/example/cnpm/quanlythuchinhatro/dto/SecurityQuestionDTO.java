package com.example.cnpm.quanlythuchinhatro.dto;

public class SecurityQuestionDTO {
	  private Integer id;
	  private String question;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public SecurityQuestionDTO(Integer id, String question) {
		super();
		this.id = id;
		this.question = question;
	}
	public SecurityQuestionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	  
	  
}