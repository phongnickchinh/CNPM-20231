package com.example.cnpm.quanlythuchinhatro.service;

import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.example.cnpm.quanlythuchinhatro.dto.JoinRoomRequestDto;
import org.springframework.data.relational.core.sql.In;

public interface JoinRoomRequestService {
    public List<Object[]> getJRRForAdmin(Integer roomId);
    public Boolean approval(Integer roomId, Integer userId, Boolean status);

    List<JoinRoomRequestDto> getAllJoinRoomRequests();

    boolean cancelJoinRoomRequest(Integer roomId, Integer userId);

}