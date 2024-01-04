package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.dto.*;
import com.example.cnpm.quanlythuchinhatro.model.Room;
import com.example.cnpm.quanlythuchinhatro.service.JoinRoomRequestService;
import com.example.cnpm.quanlythuchinhatro.service.MemberOfRoomService;
import com.example.cnpm.quanlythuchinhatro.service.RoomService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/room")
public class RoomController {

    private RoomService roomService;
    private MemberOfRoomService memberOfRoomService;
    private JoinRoomRequestService joinRoomRequestService;
    @Autowired
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
    public ResponseEntity<List<Map<String, Object>>> getAllMemberOfRoom(@RequestParam("roomId") Integer roomId) {
        List<Map<String, Object>> list = memberOfRoomService.listMemberOfRoom(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
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



    @PutMapping("/joinRoomRequest/approval")
    public ResponseEntity<String> approval(
            @RequestBody ChangeJRRStatus request) {

        // Gọi phương thức trong service để thực hiện thay đổi trạng thái
        boolean success = joinRoomRequestService.approval(
                request.getRoomId(),
                request.getUserId(),
                request.getAccept()
        );

        // Kiểm tra kết quả và trả về phản hồi tương ứng
        if(request.getAccept()){
            if(success) return ResponseEntity.ok("Đã chấp nhận yêu cầu");
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy yêu cầu");
        }
        else{
            if(success) return ResponseEntity.ok("Đã từ chối yêu cầu");
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy yêu cầudd");
        }
    }

    @GetMapping("/joinRoomRequests")
    public ResponseEntity<List<JoinRoomRequestDto>> getAllJoinRoomRequests() {
        List<JoinRoomRequestDto> requests = joinRoomRequestService.getAllJoinRoomRequests();
        return ResponseEntity.ok(requests);
    }
    
}
