package com.example.cnpm.quanlythuchinhatro.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

public class FeeWDStatusDTO {
    @Getter
    @Setter
    private String feeName;
    @Getter
    @Setter
    private Date deadline;
    @Getter
    @Setter
    private BigDecimal pricePerUser;
    @Getter
    @Setter
    private Integer status;
//    FeeWDStatusDTO(f.feeName, f.deadline, f.status, :pricePerUser)

    public FeeWDStatusDTO(String feeName, Date deadline, Integer status, BigDecimal pricePerUser) {
        this.feeName = feeName;
        this.deadline = deadline;
        this.status = status;
        this.pricePerUser = pricePerUser;
    }
}
