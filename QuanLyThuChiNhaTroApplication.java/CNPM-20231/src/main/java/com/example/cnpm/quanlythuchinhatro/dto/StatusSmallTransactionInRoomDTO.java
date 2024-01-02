package com.example.cnpm.quanlythuchinhatro.dto;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Data
@Value
public class StatusSmallTransactionInRoomDTO {
    private BigDecimal mySpent;
    private BigDecimal roomAverage;

    public StatusSmallTransactionInRoomDTO(BigDecimal mySpent, BigDecimal roomAverage) {
        this.mySpent = mySpent;
        this.roomAverage = roomAverage;
    }
}
