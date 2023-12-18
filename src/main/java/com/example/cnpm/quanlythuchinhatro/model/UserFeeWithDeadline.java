package com.example.cnpm.quanlythuchinhatro.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "User_FeeWithDeadline")
public class UserFeeWithDeadline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_id")
    private Integer userId;
    @Column(name="fee_id")
    private Integer feeId;
    @Column(name="status")
    private Integer status;

    public UserFeeWithDeadline() {
    }
    public UserFeeWithDeadline(Integer userId, Integer feeId, Integer status) {
        this.userId = userId;
        this.feeId = feeId;
        this.status = status;
    }

    
    
}
