package com.example.cnpm.quanlythuchinhatro.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "UserFeeWitthDeadline")
public class UserFeeWitthDeadline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "id_fee")
    private Integer idFee;

    @Column(name = "status")
    private String status;

    public UserFeeWitthDeadline() {
    }

    public UserFeeWitthDeadline(String status) {
        this.status = status;
    }
    public UserFeeWitthDeadline(Integer idUser, Integer idFee, String status) {
        this.idUser = idUser;
        this.idFee = idFee;
        this.status = status;
    }
}
