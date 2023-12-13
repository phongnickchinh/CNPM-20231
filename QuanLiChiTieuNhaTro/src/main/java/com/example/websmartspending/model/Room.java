package com.example.websmartspending.model;
import javax.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRoom")
    private Long idRoom;

    @ManyToOne // nhiều phòng có thể thuộc về 1 admin
    @JoinColumn(name = "idUser")
    private User user;

    @Column(name = "roomName")
    private String roomName;

    @Column(name = "address")
    private String address;

    @Column(name = "numberOfMember")
    private int numberOfMember;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<MemberOfRoom> memberOfRooms; // 1 phòng có nhiều thành viên
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<JoinRoomReQuest> joinRoomReQuests; // 1 phòng có nhiều yêu cầu tham gia
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<SmallTransaction> smallTransactions; // 1 phòng có nhiều giao dịch nhỏ
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<UserFeeWithDeadline> feeWithDeadlines; // 1 phòng có nhiều khoản phí có hạn


    // Constructors
    public Room() {
    }

    public Room(String roomName, String address, int numberOfMember) {
        this.roomName = roomName;
        this.address = address;
        this.numberOfMember = numberOfMember;
    }

    // Getters and Setters
    
}
