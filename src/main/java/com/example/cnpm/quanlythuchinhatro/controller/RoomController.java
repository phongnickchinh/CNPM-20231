package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.dto.CreateRoomRequest;
import com.example.cnpm.quanlythuchinhatro.dto.JoinRoomRequest;
import com.example.cnpm.quanlythuchinhatro.dto.RoomDto;
import com.example.cnpm.quanlythuchinhatro.model.Room;
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
    public List<Object[]> getAllMemberOfRoom(@RequestParam("roomId") Integer roomId) {
        return memberOfRoomService.listMemberOfRoom(roomId);
    }

    @GetMapping("/joinRoomRequest")
    public List<Object[]> getAllJoinRoomRequest(@RequestParam("roomId") Integer roomId) {
        return joinRoomRequestService.getJRRForAdmin(roomId);
    }

    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@RequestBody CreateRoomRequest createRequest, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            System.out.println("No login");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String username = (String)loggedInUser;
        Room room = roomService.createRoom(createRequest.getName(), createRequest.getAddress(), username);
        return ResponseEntity.ok(room);
    }
    @PostMapping("/join")
    public ResponseEntity<String> joinRoom(@RequestBody JoinRoomRequest joinRequest, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            System.out.println("No login");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String username = (String)loggedInUser;
        ResponseEntity<String> result = roomService.joinRoom(joinRequest.getRoomId(), username);
        return result;
    }


    
    
    
}
