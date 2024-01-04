package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.dto.*;
import com.example.cnpm.quanlythuchinhatro.model.Room;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;
import com.example.cnpm.quanlythuchinhatro.service.JoinRoomRequestService;
import com.example.cnpm.quanlythuchinhatro.service.MemberOfRoomService;
import com.example.cnpm.quanlythuchinhatro.service.RoomService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/room")
public class RoomController {

    private RoomService roomService;
    private MemberOfRoomService memberOfRoomService;
    private JoinRoomRequestService joinRoomRequestService;
    @Autowired
    private UserRepository userRepository;
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

    @GetMapping("/viewJRRForAdmin")
    public ResponseEntity<List<Map<String, Object>>> getAllJoinRoomRequest(@RequestParam("roomId") Integer roomId) {
        List<Map<String, Object>> list = joinRoomRequestService.getJRRForAdmin(roomId);
        return ResponseEntity.ok(list);
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
    @GetMapping("/memberOfRoom/deleteMember")
    public ResponseEntity<String> deleteMember(@RequestParam("roomId") Integer roomId, @RequestParam("userId") Integer userId) {
        Boolean success =memberOfRoomService.deleteMember(userId, roomId);
        if(success) return ResponseEntity.ok("Xóa thành viên thành công");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy thành viên");
    }

    //chuyển nhượng quyền trưởng phòng
    @GetMapping("/switchAdmin")
    public ResponseEntity<String> switchAdmin(@RequestParam("roomId") Integer roomId, @RequestParam("newAdminId") Integer userId) {
        Boolean success = roomService.switchAdmin(roomId, userId);
        if(success) return ResponseEntity.ok("Chuyển nhượng thành công");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thành công");
    }
    
    @GetMapping("/leaveRoom")
    public ResponseEntity<String> leaveRoom(@RequestParam("roomId") Integer roomId, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        Integer state= memberOfRoomService.leaveRoom(roomId, loggedInUser.toString());
        if(state == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy thành viên");
        else if(state == 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ROOM_ADMIN_CAN_NOT_LEAVE_ROOM");
        else return ResponseEntity.ok("Rời phòng thành công");
    }

    @GetMapping("/joinRoomRequests")
    public ResponseEntity<List<JoinRoomRequestDto>> getAllJoinRoomRequests() {
        List<JoinRoomRequestDto> requests = joinRoomRequestService.getAllJoinRoomRequests();
        return ResponseEntity.ok(requests);
    }

    @DeleteMapping("/joinRoomRequests/cancel")
    public ResponseEntity<String> cancelJoinRoomRequest(@RequestParam Integer roomId, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
            System.out.println("No login");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        String username = (String)loggedInUser;
        Integer userId = userRepository.convertUsernameToUserId(username);
        boolean isCancelled = joinRoomRequestService.cancelJoinRoomRequest(roomId, userId);
        if (isCancelled) {
            return ResponseEntity.ok("Yêu cầu hủy tham gia phòng đã được xử lý thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy yêu cầu tham gia phòng.");
        }
    }
    
}
