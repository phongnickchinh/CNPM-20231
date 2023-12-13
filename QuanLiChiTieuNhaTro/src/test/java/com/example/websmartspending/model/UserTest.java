package com.example.websmartspending.model;

import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.PrintWriter;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();
        user.setIdUser(1);
        user.setUserName("john_doe");
        user.setPassword("password123");
        user.setPhoneNumber("123456789");
        user.setBankAccountNumber("1234567890");
        user.setBankName("MyBank");
        user.setQrCodeurl("https://example.com/qrcode");
        user.setAvatarurl("https://example.com/avatar");

        assertEquals(1, user.getIdUser());
        assertEquals("john_doe", user.getUserName());
        assertEquals("password123", user.getPassword());
        assertEquals("123456789", user.getPhoneNumber());
        assertEquals("1234567890", user.getBankAccountNumber());
        assertEquals("MyBank", user.getBankName());
        assertEquals("https://example.com/qrcode", user.getQrCodeurl());
        assertEquals("https://example.com/avatar", user.getAvatarurl());

        // Ghi kết quả vào tệp tin
        writeResultToFile(user);
    }

    private void writeResultToFile(User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("testResult.txt", true))) {
            writer.println("IDUser: " + user.getIdUser());
            writer.println("UserName: " + user.getUserName());
            writer.println("Password: " + user.getPassword());
            writer.println("PhoneNumber: " + user.getPhoneNumber());
            writer.println("BankAccountNumber: " + user.getBankAccountNumber());
            writer.println("BankName: " + user.getBankName());
            writer.println("QrCodeurl: " + user.getQrCodeurl());
            writer.println("Avatarurl: " + user.getAvatarurl());
            writer.println("--------------"); // Phân tách giữa các bản ghi
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
