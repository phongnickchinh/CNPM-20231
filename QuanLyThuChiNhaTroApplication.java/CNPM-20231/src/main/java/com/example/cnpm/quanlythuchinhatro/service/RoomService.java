package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.Room;
import com.example.cnpm.quanlythuchinhatro.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoomService {
    public List<Object[]> listRoom(String username);
    Room createRoom(String name, String address, String username);

    ResponseEntity<String> joinRoom(Integer roomId, String username);


}
