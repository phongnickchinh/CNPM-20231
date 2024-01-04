package com.example.cnpm.quanlythuchinhatro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

public class StatusFeeWDRoomDTO {
    @Setter
    @Getter
    /*@JsonProperty("fee_id")*/
    private Integer feeId;
    @Setter
    @Getter
    private String feeName;
    @Setter
    @Getter
    private Date deadline;
    @Setter
    @Getter
    /*@JsonProperty("userId")*/
    private Integer userId;
    @Setter
    @Getter
    private BigDecimal pricePerUser;
    @Setter
    @Getter
    private Integer payStatus;
    @Setter
    @Getter
    private boolean inRoomStatus;

}
