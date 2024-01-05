package com.example.cnpm.quanlythuchinhatro.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MemberSpending {
	private Integer userId;
    private String fullname;
    private BigDecimal spend;
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public BigDecimal getSpend() {
		return spend;
	}
	public void setSpend(BigDecimal spend) {
		this.spend = spend;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public MemberSpending() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MemberSpending(Integer userId, String fullname, BigDecimal spend) {
		super();
		this.userId = userId;
		this.fullname = fullname;
		this.spend = spend;
	}

}

