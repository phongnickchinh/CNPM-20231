package com.example.cnpm.quanlythuchinhatro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

public class SmallTransactionDTO {
    @Setter
    @Getter
    private Integer id;
    @Setter
    @Getter
    private Integer roomId;
    @Setter
    @Getter
    @JsonProperty("name")
    private String itemName;
    @Setter
    @Getter
    @JsonProperty("date")
    private String transactionTime;
    @Setter
    @Getter
    private String price;
    @Setter
    @Getter
    private Integer userId;
}
