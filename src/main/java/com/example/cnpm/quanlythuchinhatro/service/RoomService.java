package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.RoomDto;
import com.example.cnpm.quanlythuchinhatro.model.Room;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface RoomService {
    List<RoomDto> listRoom(String username);
    Room createRoom(String name, String address, String username);
    ResponseEntity<String> joinRoom(Integer roomId, String username);
    public Boolean switchAdmin(Integer roomId, Integer userId);
}
