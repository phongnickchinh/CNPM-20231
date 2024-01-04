package com.example.cnpm.quanlythuchinhatro.model;
import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "JoinRoomRequest")
public class JoinRoomRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_id")
    private Integer userId;
    @Column(name="room_id")
    private Integer roomId;
    @Column(name="request_date")
    private Date requestDate;
    @Column(name="status")
    private Integer status;

    public JoinRoomRequest() {
    }
    public JoinRoomRequest(Integer userId, Integer roomId, Date requestDate, Integer status) {
        this.userId = userId;
        this.roomId = roomId;
        this.requestDate = requestDate;
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private Room room;
}