package com.example.cnpm.quanlythuchinhatro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

public class FeeWithDeadlineDTO {
    @Setter
    @Getter
    private Integer roomId;
    @Setter
    @Getter
    @JsonProperty("name")
    private String feeName;
    @Setter
    @Getter
    private Date deadline;
    @Setter
    @Getter
    private BigDecimal price;
    @Setter
    @Getter
    private Integer status;

}
