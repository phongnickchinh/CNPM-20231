package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.MemberOfRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.example.cnpm.quanlythuchinhatro.model.MemberOfRoom;
import org.springframework.stereotype.Service;
import com.example.cnpm.quanlythuchinhatro.repository.MemberOfRoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberOfRoomServiceImpl implements MemberOfRoomService {

    private final UserRepository userRepository;

    @Override
    public List<Object[]> listMemberOfRoom(Integer roomId) {
        return memberOfRoomRepository.getAllMemberOfRoom(roomId);
    }

    public void addMemberToRoom(Integer roomId, Integer userId, Integer status) {
        MemberOfRoom memberOfRoom = new MemberOfRoom();
        memberOfRoom.setRoomId(roomId);
        memberOfRoom.setUserId(userId);
        memberOfRoom.setStatus(status);
        memberOfRoomRepository.save(memberOfRoom);
    }
}
