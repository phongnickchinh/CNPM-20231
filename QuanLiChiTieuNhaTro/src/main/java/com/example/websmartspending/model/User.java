package com.example.websmartspending.model;
import javax.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUser")
    private Integer idUser;

    @Column(name = "Name")
    private String name;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "Password")
    private String password;

    @Column (name = "PhoneNumber")
    private String phoneNumber;

    @Column (name = "BankAccountNumber")
    private String bankAccountNumber;

    @Column (name = "BankName")
    private String bankName;

    @Column (name = "QrCode_url")
    private String qrCodeurl;

    @Column (name = "Avatar_url")
    private String avatarurl;

    //Các mối quan hệ
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Room> rooms;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SercurityQuestion> sercurityQuestions;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MemberOfRoom> memberOfRooms;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SmallTransaction> smallTransactions;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserFeeWithDeadline> feeWithDeadlines;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JoinRoomReQuest> joinRoomReQuests;
    // Constructors

    public User() {
    }
    public User(String name, String userName, String password, String phoneNumber, String bankAccountNumber, String bankName, String qrCodeurl, String avatarurl) {

        this.name = name;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
        this.qrCodeurl = qrCodeurl;
        this.avatarurl = avatarurl;

    }


}