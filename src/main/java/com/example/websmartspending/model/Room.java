package com.example.websmartspending.model;
import javax.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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


    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfMember() {
        return numberOfMember;
    }

    public void setNumberOfMember(int numberOfMember) {
        this.numberOfMember = numberOfMember;
    }

    public List<MemberOfRoom> getMemberOfRooms() {
        return memberOfRooms;
    }

    public void setMemberOfRooms(List<MemberOfRoom> memberOfRooms) {
        this.memberOfRooms = memberOfRooms;
    }

    public List<JoinRoomReQuest> getJoinRoomReQuests() {
        return joinRoomReQuests;
    }

    public void setJoinRoomReQuests(List<JoinRoomReQuest> joinRoomReQuests) {
        this.joinRoomReQuests = joinRoomReQuests;
    }

    public List<SmallTransaction> getSmallTransactions() {
        return smallTransactions;
    }

    public void setSmallTransactions(List<SmallTransaction> smallTransactions) {
        this.smallTransactions = smallTransactions;
    }

    public List<UserFeeWithDeadline> getFeeWithDeadlines() {
        return feeWithDeadlines;
    }

    public void setFeeWithDeadlines(List<UserFeeWithDeadline> feeWithDeadlines) {
        this.feeWithDeadlines = feeWithDeadlines;
    }
}
