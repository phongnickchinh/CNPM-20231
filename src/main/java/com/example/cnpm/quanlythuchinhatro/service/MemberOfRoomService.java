package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.MemberOfRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.MemberOfRoom;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemberOfRoomService {

    List<MemberOfRoomDTO> listMemberOfRoom(Integer roomId);
}