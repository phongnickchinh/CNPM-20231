package com.example.cnpm.quanlythuchinhatro.model;
import jakarta.persistence.*;
import lombok.Data;

//tạo một thựcthể liên kết giữa room và user
@Entity
@Data
@Table(name = "MemberOfRoom")
public class MemberOfRoom {
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

    //status
    @Column(name = "status")
    private Integer status;

    //constructor
    MemberOfRoom() {}
    MemberOfRoom(Integer roomId, Integer userId, Integer status) {
        this.roomId = roomId;
        this.userId = userId;
        this.status = status;
    }
}
