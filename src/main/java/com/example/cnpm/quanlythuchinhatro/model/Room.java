package com.example.cnpm.quanlythuchinhatro.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //room name
    @Column(name = "name")
    private String roomName;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "address")
    private String address;

}
