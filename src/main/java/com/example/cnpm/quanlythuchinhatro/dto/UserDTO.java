package com.example.cnpm.quanlythuchinhatro.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {
    @Setter
    @Getter
    private String phoneNumber;
    @Setter
    @Getter
    private String bankNumber;
    @Getter
    @Setter
    private String bankName;
    @Getter
    @Setter
    private String name;
}
