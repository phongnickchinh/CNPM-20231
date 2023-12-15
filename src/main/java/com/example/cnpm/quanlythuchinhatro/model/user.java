package com.example.cnpm.quanlythuchinhatro.model;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "User")
public class User {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;
    
        //username
        @Column(name = "username")
        private String username;
    
        //password
        @Column(name = "password")
        private String password;
    
        //name
        @Column(name = "name")
        private String name;
    
        //phone
        @Column(name = "phone")
        private String phone;

        //bank_account_number
        @Column(name = "bank_account_number")
        private String bankAccountNumber;

        //bank_name
        @Column(name = "bank_name")
        private String bankName;

        //qr_code_url
        @Column(name = "qr_code_url")
        private String qrCodeUrl;

        //avatar_url
        @Column(name = "avatar_url")
        private String avatarUrl;
    
        //constructor
        public User() {
        }
        // Constructor cho các thuộc tính bắt buộc
    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    // Constructor cho tất cả các thuộc tính
    public User(String username, String password, String name, String phone, String bankAccountNumber, String bankName, String qrCodeUrl, String avatarUrl) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
        this.qrCodeUrl = qrCodeUrl;
        this.avatarUrl = avatarUrl;
    }

    // Constructor cho các thuộc tính bắt buộc và một số thuộc tính mặc định
    public User(String username, String password, String name, String phone, String bankAccountNumber) {
        this(username, password, name, phone, bankAccountNumber, "DefaultBankName", "DefaultQrCodeUrl", "DefaultAvatarUrl");
    }
    
}
