package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.RoomDto;
import com.example.cnpm.quanlythuchinhatro.model.Room;
import com.example.cnpm.quanlythuchinhatro.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> listRoom(String username) {
        List<Room> rooms = roomRepository.listRoom(username);

        List<RoomDto> roomDtos = new ArrayList<>();
        for (Room r : rooms) {
            RoomDto rd = new RoomDto();
            rd.setId(r.getId());
            rd.setRoomName(r.getRoomName());
            rd.setAddress(r.getAddress());
            rd.setIsAdmin(r.getAdminId() != null);
            roomDtos.add(rd);
        }

//        return rooms.stream().map(r -> {
//            RoomDto rd = new RoomDto();
//            rd.setId(r.getId());
//            rd.setRoomName(r.getRoomName());
//            rd.setAddress(r.getAddress());
//            rd.setIsAdmin(r.getAdminId() != null);
//            return rd;
//        }).toList();
        return roomDtos;
    }

}
