package com.example.cnpm.quanlythuchinhatro.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TransactionStatisticsResponse {

    private BigDecimal total;
    private BigDecimal average;
    private List<MemberSpending> memberSpendings;

    public TransactionStatisticsResponse(BigDecimal total, BigDecimal average, List<MemberSpending> memberSpendings) {
        this.total = total;
        this.average = average;
        this.memberSpendings = memberSpendings;
    }
}