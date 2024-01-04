package com.example.cnpm.quanlythuchinhatro.service;

import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.example.cnpm.quanlythuchinhatro.dto.JoinRoomRequestDto;
import org.springframework.data.relational.core.sql.In;
import java.util.Map;

public interface JoinRoomRequestService {

    List<JoinRoomRequestDto> getAllJoinRoomRequests();

    boolean cancelJoinRoomRequest(Integer roomId, Integer userId);

    public List<Map<String, Object>> getJRRForAdmin(Integer roomId);
    public Boolean approval(Integer roomId, Integer userId, Boolean status);

}