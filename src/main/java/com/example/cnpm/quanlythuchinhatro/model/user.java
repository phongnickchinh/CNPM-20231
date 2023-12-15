package com.example.cnpm.quanlythuchinhatro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.Id;

@Entity
@Data
public class user {
    @Id
    @Column(name = "userID")
    private Long id;
}
