package com.example.cnpm.quanlythuchinhatro.service;

import java.util.List;
import com.example.cnpm.quanlythuchinhatro.repository.JoinRoomRequestRepository;
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
}
