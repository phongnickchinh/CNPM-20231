package com.example.cnpm.quanlythuchinhatro.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //room name
    @Column(name = "name")
    private String roomName;

    //numberofmember
    @Column(name = "number_of_member")
    private Integer numberOfMember;

    @Column(name = "user_id")
    private Integer userId;



    //constructor
    public Room() {
    }
    public Room(String roomName, Integer numberOfMember, Integer userId) {
        this.roomName = roomName;
        this.numberOfMember = numberOfMember;
        this.userId = userId;
    }


    
}
