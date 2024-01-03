package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.MemberOfRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public List<MemberOfRoomDTO> listMemberOfRoom(Integer roomId) {
        List<User> members = userRepository.getAllMemberOfRoom(roomId);

        List<MemberOfRoomDTO> memberDTOs = new ArrayList<>();
        return members.stream().map(m -> {
            MemberOfRoomDTO memb = new MemberOfRoomDTO();
            memb.setId(m.getId());
            memb.setName(m.getName());
            memb.setPhoneNumber(m.getPhoneNumber());
            memb.setBankName(m.getBankName());
            memb.setBankNumber(m.getBankAccountNumber());
            memb.setAvatarUrl(m.getAvatarUrl());

            memberDTOs.add(memb);
            return memb;
        }).toList();
    }
}
