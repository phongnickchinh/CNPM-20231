package com.example.cnpm.quanlythuchinhatro.model;
import javax.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "FeeWithDeadline")
public class FeeWithDeadline {
    //id
    //room_id
    //name
    //money
    //deadline
    //type

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_room")
    private Integer idRoom;

    @Column(name = "name")
    private String name;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "deadline")
    private Date deadline;
    
    @Column(name = "type")
    private Integer type;

    public FeeWithDeadline() {
    }
    public FeeWithDeadline(Integer idRoom, String name, BigDecimal money, Date deadline, Integer type) {
        this.idRoom = idRoom;
        this.name = name;
        this.money = money;
        this.deadline = deadline;
        this.type = type;
    }

    
}
