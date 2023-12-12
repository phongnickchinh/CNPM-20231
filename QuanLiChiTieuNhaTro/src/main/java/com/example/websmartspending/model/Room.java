package com.example.websmartspending.model;
import javax.persistence.*;

@Entity
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRoom")
    private Long idRoom;

    @Column(name = "roomName")
    private String roomName;

    @Column(name = "address")
    private String address;

    @Column(name = "numberOfMember")
    private int numberOfMember;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
