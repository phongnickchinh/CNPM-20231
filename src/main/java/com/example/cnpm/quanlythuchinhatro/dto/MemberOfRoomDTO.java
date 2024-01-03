package com.example.cnpm.quanlythuchinhatro.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

public class MemberOfRoomDTO {
    @Setter
    @Getter
    private Integer id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String phoneNumber;
    @Setter
    @Getter
    private String bankNumber;
    @Setter
    @Getter
    private String bankName;
    @Setter
    @Getter
    private String avatarUrl;

}
