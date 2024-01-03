package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.RoomDto;
import com.example.cnpm.quanlythuchinhatro.model.JoinRoomRequest;
import com.example.cnpm.quanlythuchinhatro.model.Room;
import com.example.cnpm.quanlythuchinhatro.repository.JoinRoomRequestRepository;
import com.example.cnpm.quanlythuchinhatro.repository.MemberOfRoomRepository;
import com.example.cnpm.quanlythuchinhatro.repository.RoomRepository;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService{
    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MemberOfRoomService memberOfRoomService;
    @Autowired
    private MemberOfRoomRepository memberOfRoomRepository;
    @Autowired
    private JoinRoomRequestRepository joinRoomRequestRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> listRoom(String username) {
        List<Room> rooms = roomRepository.listRoom(username);

        List<RoomDto> roomDtos = new ArrayList<>();
        for (Room r : rooms) {
            RoomDto rd = new RoomDto();
            rd.setId(r.getId());
            rd.setRoomName(r.getRoomName());
            rd.setAddress(r.getAddress());
            rd.setIsAdmin(r.getAdminId() != null);
            roomDtos.add(rd);
        }

//        return rooms.stream().map(r -> {
//            RoomDto rd = new RoomDto();
//            rd.setId(r.getId());
//            rd.setRoomName(r.getRoomName());
//            rd.setAddress(r.getAddress());
//            rd.setIsAdmin(r.getAdminId() != null);
//            return rd;
//        }).toList();
        return roomDtos;
    }

    public Room createRoom(String name, String address, String username) {
        Integer adminId = userRepository.convertUsernameToUserId(username);

        Room room = new Room();
        room.setRoomName(name);
        room.setAddress(address);
        room.setAdminId(adminId);
        roomRepository.save(room);

        memberOfRoomService.addMemberToRoom(room.getId(),adminId, 1);
        return room;
    }

    public ResponseEntity<String> joinRoom(Integer roomId, String username) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ROOM_ID_DOESNOT_EXIST");
        }

        // Giả sử bạn có một cách để kiểm tra nếu người dùng đã ở trong phòng
        if (userAlreadyInRoom(userRepository.convertUsernameToUserId(username), roomId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("USER_HAS_BEEN_IN_ROOM");
        }
        Integer userId = userRepository.convertUsernameToUserId(username);
        Optional<JoinRoomRequest> request = joinRoomRequestRepository.findByUserIdAndRoomId(userId, roomId);
        if (request.isEmpty()) {
            JoinRoomRequest joinRoomRequest = new JoinRoomRequest();
            joinRoomRequest.setRoomId(roomId);
            joinRoomRequest.setUserId(userId);
            joinRoomRequest.setStatus(1);
            joinRoomRequestRepository.save(joinRoomRequest);
        }
        else {
            JoinRoomRequest joinRoomRequest = request.get();
            joinRoomRequest.setStatus(1);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Thành Công");
    }

    private boolean userAlreadyInRoom(Integer userId, Integer roomId) {

        return memberOfRoomRepository.existsByUserIdAndRoomIdAndStatus(userId, roomId, 1);
    }
}
