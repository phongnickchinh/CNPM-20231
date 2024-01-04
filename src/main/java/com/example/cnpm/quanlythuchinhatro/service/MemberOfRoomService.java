package com.example.cnpm.quanlythuchinhatro.service;

import java.util.List;
import java.util.Map;

public interface MemberOfRoomService {

    List<Map<String, Object>> listMemberOfRoom(Integer roomId);

    void addMemberToRoom(Integer roomId, Integer userId, Integer status);
    public Boolean deleteMember(Integer roomId, Integer userId);
    public void addMember(Integer roomId, Integer userId, Integer status, java.sql.Date joinDate);
    public Integer leaveRoom(Integer roomId, String username);
}