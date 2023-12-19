package com.example.cnpm.quanlythuchinhatro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class QuanLyThuChiNhaTroApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanLyThuChiNhaTroApplication.class, args);
    }

}
