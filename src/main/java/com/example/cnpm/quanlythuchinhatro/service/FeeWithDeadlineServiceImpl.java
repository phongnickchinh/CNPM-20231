package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.*;
import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.Room;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.repository.FeeWithDeadlineRepository;
import com.example.cnpm.quanlythuchinhatro.repository.MemberOfRoomRepository;
import com.example.cnpm.quanlythuchinhatro.repository.UserFeeWithDeadlineRepository;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FeeWithDeadlineServiceImpl implements FeeWithDeadlineService{
    private final FeeWithDeadlineRepository feeWithDeadlineRepository;
    private final MemberOfRoomRepository memberOfRoomRepository;
    private final UserFeeWithDeadlineRepository userFeeWithDeadlineRepository;
    public FeeWithDeadlineServiceImpl(FeeWithDeadlineRepository feeWithDeadlineRepository, MemberOfRoomRepository memberOfRoomRepository, UserFeeWithDeadlineRepository userFeeWithDeadlineRepository) {
        this.feeWithDeadlineRepository = feeWithDeadlineRepository;
        this.memberOfRoomRepository = memberOfRoomRepository;
        this.userFeeWithDeadlineRepository = userFeeWithDeadlineRepository;
    }

    @Override
    public ResponseEntity<?> createFeeWithDeadline(FeeWithDeadlineDTO feeWithDeadlineDTO) {
        FeeWithDeadline fee = new FeeWithDeadline();
        fee.setRoomId(feeWithDeadlineDTO.getRoomId());
        fee.setFeeName(feeWithDeadlineDTO.getName());
        fee.setMoney(feeWithDeadlineDTO.getPrice());
        fee.setDeadline(feeWithDeadlineDTO.getDeadline());
        feeWithDeadlineRepository.save(fee);

        List<Integer> userIds = memberOfRoomRepository.findUserIdsByRoomId(fee.getRoomId());

        for (Integer userId : userIds) {
            UserFeeWithDeadline userFee = new UserFeeWithDeadline();
            userFee.setUserId(userId);
            userFee.setFeeId(fee.getId());
            userFee.setStatus(0);
            userFeeWithDeadlineRepository.save(userFee);
        }

        return ResponseEntity.ok("Tạo giao thành công");
    }
    @Override
    public List<ListFeeWDDTO> listFeeWDByRoomId(Integer roomId) {
        List<FeeWithDeadline> fee = feeWithDeadlineRepository.findAllByRoomId(roomId);

        List<ListFeeWDDTO> feeDTOs = new ArrayList<>();
        for(FeeWithDeadline f : fee) {
            ListFeeWDDTO list = new ListFeeWDDTO();
            list.setId(f.getId());
            list.setFeeName(f.getFeeName());
            list.setMoney(f.getMoney());
            list.setDeadline(f.getDeadline());

            feeDTOs.add(list);
        }
        return feeDTOs;
    }

    @Override
    public void deleteFeeWithDeadline(Integer id) {
        feeWithDeadlineRepository.deleteById(id);
    }



    @Override
    public FeeWithDeadline updateFeeWithDeadline(Integer id, ListFeeWDDTO listFeeWDDTO) {

        Optional<FeeWithDeadline> feeWithDeadlineDB = feeWithDeadlineRepository.findById(id);

        if (feeWithDeadlineDB.isPresent()) {
            FeeWithDeadline fee = feeWithDeadlineDB.get();
            if (listFeeWDDTO.getFeeName() != null) {
                fee.setFeeName(listFeeWDDTO.getFeeName());
            }
            if (listFeeWDDTO.getDeadline() != null) {
                fee.setDeadline(listFeeWDDTO.getDeadline());
            }
            if (listFeeWDDTO.getMoney() != null) {
                fee.setMoney(listFeeWDDTO.getMoney());
            }

            feeWithDeadlineRepository.save(fee);
            return fee;
        } else {
            return null;
        }
    }

    @Override
    public List<Map<String,Object>> userStatusFeeWD(Integer roomId, String username){
        Integer userId = feeWithDeadlineRepository.convertUsernameToUserId(username);
        return feeWithDeadlineRepository.userStatusFee(roomId, userId);

    }
    public List<Map<String,Object>> roomStatusFeeWD(Integer roomId) {
        return feeWithDeadlineRepository.getList(roomId);
    }

}
