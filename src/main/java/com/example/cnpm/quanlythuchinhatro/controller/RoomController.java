package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.dto.MemberOfRoomDTO;
import com.example.cnpm.quanlythuchinhatro.dto.RoomDto;
import com.example.cnpm.quanlythuchinhatro.service.JoinRoomRequestService;
import com.example.cnpm.quanlythuchinhatro.service.MemberOfRoomService;
import com.example.cnpm.quanlythuchinhatro.service.RoomService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<List<RoomDto>>  listRoom(HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        List<RoomDto> list = roomService.listRoom(loggedInUser.toString());
        return ResponseEntity.status(HttpStatus.OK).body(list);
//        return ResponseEntity.ok(list);
    }
    @GetMapping("/memberOfRoom")
    public ResponseEntity<List<MemberOfRoomDTO>> getAllMemberOfRoom(@RequestParam("roomId") Integer roomId) {
        List<MemberOfRoomDTO> list = memberOfRoomService.listMemberOfRoom(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/joinRoomRequest")
    public List<Object[]> getAllJoinRoomRequest(@RequestParam("roomId") Integer roomId) {
        return joinRoomRequestService.getJRRForAdmin(roomId);
    }
    
}
