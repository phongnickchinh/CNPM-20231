package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.repository.MemberOfRoomRepository;
import com.example.cnpm.quanlythuchinhatro.repository.RoomRepository;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.example.cnpm.quanlythuchinhatro.model.MemberOfRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberOfRoomServiceImpl implements MemberOfRoomService {
    @Autowired
    private final MemberOfRoomRepository memberOfRoomRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<Map<String, Object>> listMemberOfRoom(Integer roomId) {
        return memberOfRoomRepository.getAllMemberOfRoom(roomId);
    }

    public void addMemberToRoom(Integer roomId, Integer userId, Integer status) {
        MemberOfRoom memberOfRoom = new MemberOfRoom();
        memberOfRoom.setRoomId(roomId);
        memberOfRoom.setUserId(userId);
        memberOfRoom.setStatus(status);
        memberOfRoomRepository.save(memberOfRoom);
    }
    @Override
    public void addMember(Integer roomId, Integer userId, Integer status, java.sql.Date joinDate) {
        MemberOfRoom memberOfRoom = memberOfRoomRepository.findByUserIdAndRoomId(userId, roomId);
        if(memberOfRoom == null){
            memberOfRoom = new MemberOfRoom();
            memberOfRoom.setRoomId(roomId);
            memberOfRoom.setUserId(userId);
            memberOfRoom.setStatus(status);
            memberOfRoom.setJoinDate(joinDate);
            memberOfRoomRepository.save(memberOfRoom);
        }
        else{
            memberOfRoom.setStatus(status);
            memberOfRoom.setJoinDate(joinDate);
            memberOfRoomRepository.save(memberOfRoom);
        }
    }

    @Override
    public Boolean deleteMember(Integer roomId, Integer userId) {
        MemberOfRoom memberOfRoom = memberOfRoomRepository.findByUserIdAndRoomId(roomId,userId);
        if(memberOfRoom == null) return false;
        else{
            memberOfRoom.setStatus(0);
            //lấy ngày hiện tại đến ngày
            memberOfRoom.setOutDate(new java.sql.Date(System.currentTimeMillis()));
            memberOfRoomRepository.save(memberOfRoom);
            return true;
        }
        
    }

    @Override
    public Integer leaveRoom(Integer roomId, String username) {
        Integer userId= userRepository.convertUsernameToUserId(username);
        MemberOfRoom memberOfRoom = memberOfRoomRepository.findByUserIdAndRoomId(userId, roomId);
        Integer countMember = memberOfRoomRepository.countMember(roomId);
        Integer adminId = roomRepository.getAdminId(roomId);
        if(memberOfRoom == null) return 0;
        if(countMember <= 1){//luôn luôn được rời phòng
            memberOfRoom.setStatus(0);
            memberOfRoom.setOutDate(new java.sql.Date(System.currentTimeMillis()));
            memberOfRoomRepository.save(memberOfRoom);
            return 2;
        }
        else{
            if(adminId.equals(userId)){//nếu là admin
                return 1;
            }
            else{//nếu là thành viên
                memberOfRoom.setStatus(0);
                memberOfRoom.setOutDate(new java.sql.Date(System.currentTimeMillis()));
                memberOfRoomRepository.save(memberOfRoom);
                return 3;
            }

        }

    }
}
