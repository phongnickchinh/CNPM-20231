package com.example.cnpm.quanlythuchinhatro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RoomDto {
    private Integer id;
    private String roomName;
    private String address;
    private Boolean isAdmin;
}
