package com.example.websmartspending.model;
import javax.persistence.*;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "MemberOfRoom")
public class MemberOfRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMemberOfRoom")
    private Long idMemberOfRoom;

    @ManyToOne // nhiều thành viên có thể thuộc về 1 phòng
    @JoinColumn(name = "IDRoom", insertable = false, updatable = false)
    private Room room;

    @ManyToOne // nhiều thành viên là cùng 1 người dùng
    @JoinColumn(name = "IDUser", insertable = false, updatable = false)
    private User user;

    @Column(name = "Status")
    private int status;

    @Column(name = "JoinDate")
    private Date joinDate;

    @Column(name = "OutDate")
    private Date outDate;

    // Constructors
    public MemberOfRoom() {
    }

    public MemberOfRoom(Room room, User user) {
        this.room = room;
        this.user = user;
    }

    // Getters and Setters tự động được tạo bởi lombok
}
