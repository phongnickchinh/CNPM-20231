package com.example.cnpm.quanlythuchinhatro.model;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
//tạo một thựcthể liên kết giữa room và user
@Entity
@Data
@Table(name = "member_of_room")
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

    //join_date
    @Column(name = "join_date")
    private Date joinDate;

    //out_date
    @Column(name = "out_date")
    private Date outDate;

    //status
    @Column(name = "status")
    private Integer status;
}
