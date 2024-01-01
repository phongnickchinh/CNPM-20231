package com.example.cnpm.quanlythuchinhatro.service;

import org.springframework.stereotype.Service;
import com.example.cnpm.quanlythuchinhatro.repository.MemberOfRoomRepository;

import java.util.List;
@Service
public class MemberOfRoomServiceImpl implements MemberOfRoomService {

    private final MemberOfRoomRepository memberOfRoomRepository;
    public MemberOfRoomServiceImpl(MemberOfRoomRepository memberOfRoomRepository) {
        this.memberOfRoomRepository = memberOfRoomRepository;
    }
    @Override
    public List<Object[]> listMemberOfRoom(Integer roomId) {
        return memberOfRoomRepository.getAllMemberOfRoom(roomId);
    }
    
}
