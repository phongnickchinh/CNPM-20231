package com.example.cnpm.quanlythuchinhatro.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.cnpm.quanlythuchinhatro.dto.JoinRoomRequestDto;
import com.example.cnpm.quanlythuchinhatro.repository.JoinRoomRequestRepository;
import com.example.cnpm.quanlythuchinhatro.repository.MemberOfRoomRepository;
import com.example.cnpm.quanlythuchinhatro.model.JoinRoomRequest;
import com.example.cnpm.quanlythuchinhatro.model.MemberOfRoom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinRoomRequestServiceImpl implements JoinRoomRequestService{

    @Autowired
    private JoinRoomRequestRepository joinRoomRequestRepository;
    private MemberOfRoomRepository  memberOfRoomRepository;
    public JoinRoomRequestServiceImpl(JoinRoomRequestRepository joinRoomRequestRepository, MemberOfRoomRepository memberOfRoomRepository) {
        this.joinRoomRequestRepository = joinRoomRequestRepository;
        this.memberOfRoomRepository = memberOfRoomRepository;
    }

    @Override
    public List<Map<String, Object>> getJRRForAdmin(Integer roomId) {
        return joinRoomRequestRepository.getJRRForAdmin(roomId);
    }
    @Override
    public Boolean approval(Integer roomId, Integer userId, Boolean status) {
        JoinRoomRequest jrr= joinRoomRequestRepository.findByUserIdAndRoomId(userId, roomId);
        if(jrr == null) return false;
        else{
            if(status == true){
                MemberOfRoom newMember = new MemberOfRoom();
                jrr.setStatus(2);
                newMember.setRoomId(roomId);
                newMember.setUserId(userId);
                newMember.setStatus(1);
                newMember.setJoinDate(new java.sql.Date(System.currentTimeMillis()));
                joinRoomRequestRepository.save(jrr);
                memberOfRoomRepository.save(newMember);
                return true;
            }
            else{
                jrr.setStatus(0);
                joinRoomRequestRepository.save(jrr);
                return true;
            }
        }
    }

    public List<JoinRoomRequestDto> getAllJoinRoomRequests() {
        List<JoinRoomRequest> requests = joinRoomRequestRepository.findAll();
        return requests.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JoinRoomRequestDto convertToDto(JoinRoomRequest request) {
        JoinRoomRequestDto dto = new JoinRoomRequestDto();
        dto.setRoomName(request.getRoom().getRoomName());
        dto.setRequestDate(request.getRequestDate());
        dto.setStatus(request.getStatus());
        dto.setRoomId(request.getRoomId());
        return dto;
    }

    public boolean cancelJoinRoomRequest(Integer roomId, Integer userId) {
        Optional<JoinRoomRequest> joinRoomRequestOptional = joinRoomRequestRepository.findByRoomIdAndUserId(roomId, userId);
        if (joinRoomRequestOptional.isPresent()) {
            JoinRoomRequest joinRoomRequest = joinRoomRequestOptional.get();
            joinRoomRequest.setStatus(3);
            joinRoomRequestRepository.save(joinRoomRequest);
            return true;
        }
        return false;
    }
}
