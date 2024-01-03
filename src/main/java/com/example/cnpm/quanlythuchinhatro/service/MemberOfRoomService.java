package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.MemberOfRoom;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemberOfRoomService {

    public List<Object[]> listMemberOfRoom(Integer roomId);

    void addMemberToRoom(Integer roomId, Integer userId, Integer status);
}