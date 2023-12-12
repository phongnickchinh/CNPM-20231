package com.example.websmartspending.model;
import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUser")
    private Integer idUser;

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

    @Column (name = "QrCodeurl")
    private String qrCodeurl;

    @Column (name = "Avatarurl")
    private String avatarurl;

    public User() {
    }
    public User(String userName, String password, String phoneNumber, String bankAccountNumber, String bankName, String qrCodeurl, String avatarurl) {

        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
        this.qrCodeurl = qrCodeurl;
        this.avatarurl = avatarurl;

    }

    public Integer getIdUser() {
        return idUser;
    }
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getqrCodeurl() {
        return qrCodeurl;
    }
    public void setQrCodeurl(String qrCodeurl) {
        this.qrCodeurl = qrCodeurl;
    }
    public String getAvatarurl() {
        return avatarurl;
    }
    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }
    public String toString() {
        return "user [id=" + idUser + ", userName=" + userName + ", password=" + password + ", phoneNumber=" + phoneNumber + ", bankAccountNumber=" + bankAccountNumber + ", bankName=" + bankName + ", qrCodeurl=" + qrCodeurl + ", avatarurl=" + avatarurl + "]";
    }

}