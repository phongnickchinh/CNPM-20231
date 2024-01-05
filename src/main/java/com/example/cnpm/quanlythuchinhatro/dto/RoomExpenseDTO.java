package com.example.cnpm.quanlythuchinhatro.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomExpenseDTO {
    private String roomName;
    private BigDecimal totalSpending;
}
