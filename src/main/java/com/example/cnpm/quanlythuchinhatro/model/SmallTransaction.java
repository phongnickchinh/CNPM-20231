package com.example.cnpm.quanlythuchinhatro.model;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "SmallTransaction")
public class SmallTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //room_id
    @Column(name = "room_id")
    private Integer roomId;

    //user_id
    @Column(name = "user_id")
    private Integer userId;

    //item_name
    @Column(name = "item_name")
    private String itemName;

    @Column(name = "transaction_time")
    private String transactionTime; // Format to YYYY-MM-DD

  
    @Column(name = "price")
    private BigDecimal price;

    //note
    @Column(name = "note")
    private String note;

    //constructor
    public SmallTransaction() {
    }

    public SmallTransaction(Integer roomId, Integer userId, String itemName, String transactionTime, BigDecimal price, String note) {
        this.roomId = roomId;
        this.userId = userId;
        this.itemName = itemName;
        this.transactionTime = transactionTime;
        this.price = price;
        this.note = note;
    }

    
}