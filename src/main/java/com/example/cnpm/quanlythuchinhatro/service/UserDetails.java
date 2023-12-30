package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;
    public UserDetails(User user) {
        this.name = user.getName();
        this.password = user.getPassword();
        if(user.getRoles() != null) {
            authorities = Arrays.stream(user.getRoles().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } else {
            this.authorities = Collections.emptyList();
        }

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
