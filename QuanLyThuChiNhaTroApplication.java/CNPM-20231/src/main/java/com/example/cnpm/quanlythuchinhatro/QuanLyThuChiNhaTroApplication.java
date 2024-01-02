package com.example.cnpm.quanlythuchinhatro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class/*, UserDetailsServiceAutoConfiguration.class*/})
public class QuanLyThuChiNhaTroApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanLyThuChiNhaTroApplication.class, args);
    }
}
