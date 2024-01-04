package com.example.cnpm.quanlythuchinhatro.dto;

import java.math.BigDecimal;
import java.util.List;

public class MonthlyStatsResponse {
	private BigDecimal total;
    private BigDecimal average;
    private List<MemberSpending> memberSpendings;
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getAverage() {
		return average;
	}
	public void setAverage(BigDecimal average) {
		this.average = average;
	}
	public List<MemberSpending> getMemberSpendings() {
		return memberSpendings;
	}
	public void setMemberSpendings(List<MemberSpending> memberSpendings) {
		this.memberSpendings = memberSpendings;
	}
	public MonthlyStatsResponse(BigDecimal total, BigDecimal average, List<MemberSpending> memberSpendings) {
		super();
		this.total = total;
		this.average = average;
		this.memberSpendings = memberSpendings;
	}
	public MonthlyStatsResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
