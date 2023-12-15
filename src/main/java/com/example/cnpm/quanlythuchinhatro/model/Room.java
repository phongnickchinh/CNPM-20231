package com.example.cnpm.quanlythuchinhatro.model;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "Room")
public class Room {
    //id
    //user_id
    //name
    //number_of_member
    //address

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_member")
    private Integer numberOfMember;
    
    @Column(name = "address")
    private String address;

    public Room() {
    }
    public Room(Integer idUser, String name, Integer numberOfMember, String address) {
        this.idUser = idUser;
        this.name = name;
        this.numberOfMember = numberOfMember;
        this.address = address;
    }
    
}
