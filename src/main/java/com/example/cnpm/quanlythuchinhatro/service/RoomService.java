package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.RoomDto;

import java.util.List;

public interface RoomService {
    List<RoomDto> listRoom(String username);
}
