package com.example.cnpm.quanlythuchinhatro.service;

import java.util.List;
import com.example.cnpm.quanlythuchinhatro.repository.JoinRoomRequestRepository;
import com.example.cnpm.quanlythuchinhatro.model.JoinRoomRequest;
import org.springframework.stereotype.Service;

@Service
public class JoinRoomRequestServiceImpl implements JoinRoomRequestService{

    private JoinRoomRequestRepository joinRoomRequestRepository;
    public JoinRoomRequestServiceImpl(JoinRoomRequestRepository joinRoomRequestRepository) {
        this.joinRoomRequestRepository = joinRoomRequestRepository;
    }

    @Override
    public List<Object[]> getJRRForAdmin(Integer roomId) {
        return joinRoomRequestRepository.getJRRForAdmin(roomId);
    }
    @Override
    public Boolean approval(Integer roomId, Integer userId, Boolean status) {
        JoinRoomRequest jrr= joinRoomRequestRepository.findByUserIdAndRoomId(userId, roomId);
        if(jrr == null) return false;
        else{
            if(status == true){
                jrr.setStatus(2);
                joinRoomRequestRepository.save(jrr);
                return true;
            }
            else{
                jrr.setStatus(0);
                joinRoomRequestRepository.save(jrr);
                return true;
            }
        }
    }
}
