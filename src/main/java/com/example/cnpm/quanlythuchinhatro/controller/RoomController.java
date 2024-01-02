package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.service.JoinRoomRequestService;
import com.example.cnpm.quanlythuchinhatro.service.MemberOfRoomService;
import com.example.cnpm.quanlythuchinhatro.service.RoomService;
import jakarta.servlet.http.HttpSession;
import net.sf.jsqlparser.statement.select.Join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;
    private MemberOfRoomService memberOfRoomService;
    private JoinRoomRequestService joinRoomRequestService;
    public RoomController(RoomService roomService, MemberOfRoomService memberOfRoomService, JoinRoomRequestService joinRoomRequestService) {
        this.roomService = roomService;
        this.memberOfRoomService = memberOfRoomService;
        this.joinRoomRequestService = joinRoomRequestService;
    }

    @GetMapping("/listRoom")
    public List<Object[]> listRoom(HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        return roomService.listRoom((String) loggedInUser);
    }
    @GetMapping("/memberOfRoom")
    public List<Object[]> getAllMemberOfRoom(@RequestParam("roomId") Integer roomId) {
        return memberOfRoomService.listMemberOfRoom(roomId);
    }

    @GetMapping("/joinRoomRequest")
    public List<Object[]> getAllJoinRoomRequest(@RequestParam("roomId") Integer roomId) {
        return joinRoomRequestService.getJRRForAdmin(roomId);
    }
    
    
    
}
