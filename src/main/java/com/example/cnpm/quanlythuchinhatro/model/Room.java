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

    @Column(name = "admin_id")
    private Integer admin_id;

    @Column(name = "address")
    private String address;

}
