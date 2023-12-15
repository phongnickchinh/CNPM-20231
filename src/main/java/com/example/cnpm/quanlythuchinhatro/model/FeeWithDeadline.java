package com.example.cnpm.quanlythuchinhatro.model;

import javax.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "FeeWithDeadline")
public class FeeWithDeadline {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    //room_id
    @Column(name = "room_id")
    private Integer roomId;

    //name
    @Column(name = "fee_name")
    private String feeName;
    //deadline
    @Column(name = "deadline")
    private Date deadline;
    //status
    @Column(name = "status")
    private Integer status;
    //money
    @Column(name = "money")
    private BigDecimal money;
    //type
    @Column(name = "type")
    private Integer type;

    //constructor

    public FeeWithDeadline() {
    }
    public FeeWithDeadline(Integer roomId, String feeName, Date deadline, Integer status, BigDecimal money, Integer type) {
        this.roomId = roomId;
        this.feeName = feeName;
        this.deadline = deadline;
        this.status = status;
        this.money = money;
        this.type = type;
    }
    
}
