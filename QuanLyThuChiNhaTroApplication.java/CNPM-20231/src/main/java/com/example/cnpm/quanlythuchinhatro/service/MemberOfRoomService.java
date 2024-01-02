package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.MemberOfRoom;
import com.example.cnpm.quanlythuchinhatro.repository.MemberOfRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class MemberOfRoomService {

    @Autowired
    private MemberOfRoomRepository memberOfRoomRepository;

    public void addMemberToRoom(Integer roomId, Integer userId, Integer status) {
        MemberOfRoom memberOfRoom = new MemberOfRoom();
        memberOfRoom.setRoomId(roomId);
        memberOfRoom.setUserId(userId);
        memberOfRoom.setStatus(status);
        memberOfRoomRepository.save(memberOfRoom);
    }
}

