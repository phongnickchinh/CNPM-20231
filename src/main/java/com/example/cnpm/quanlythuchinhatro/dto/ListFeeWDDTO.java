package com.example.cnpm.quanlythuchinhatro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

public class ListFeeWDDTO {
    @Setter
    @Getter
    @JsonProperty("id")
    private Integer id;
    @Setter
    @Getter
    @JsonProperty("name")
    private String feeName;
    @Setter
    @Getter
    private Date deadline;
    @Setter
    @Getter
    @JsonProperty("price")
    private BigDecimal money;
}
