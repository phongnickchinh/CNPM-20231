package com.example.cnpm.quanlythuchinhatro.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class JoinRoomRequestDto {
    private String roomName;
    private Date requestDate;
    private Integer status;
    private Integer roomId;
}
