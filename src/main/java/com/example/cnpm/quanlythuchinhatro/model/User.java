package com.example.cnpm.quanlythuchinhatro.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "roles")
    private String roles;

//    public User() {
//    }
//
//    // Constructor cho các thuộc tính bắt buộc
//    public User(String username, String password, String name) {
//        this.username = username;
//        this.password = password;
//        this.name = name;
//    }
//
//    // Constructor cho tất cả các thuộc tính
//
//    public User(String username, String password, String name, String phoneNumber, String bankAccountNumber, String bankName, String qrCodeUrl, String avatarUrl, String roles) {
//        this.username = username;
//        this.password = password;
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.bankAccountNumber = bankAccountNumber;
//        this.bankName = bankName;
//        this.qrCodeUrl = qrCodeUrl;
//        this.avatarUrl = avatarUrl;
//        this.roles = roles;
//    }
//
//    // Constructor cho các thuộc tính bắt buộc và một số thuộc tính mặc định
//    public User(String username, String password, String name, String phoneNumber, String bankAccountNumber, String roles) {
//        this(username, password, name, phoneNumber, bankAccountNumber, "DefaultBankName", "DefaultQrCodeUrl", "DefaultAvatarUrl", "ROLE_USER");
//    }

}
