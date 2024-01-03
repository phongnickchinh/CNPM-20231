package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.FeeWDStatusDTO;
import com.example.cnpm.quanlythuchinhatro.dto.FeeWithDeadlineDTO;
import com.example.cnpm.quanlythuchinhatro.dto.StatusSmallTransactionInRoomDTO;
import com.example.cnpm.quanlythuchinhatro.dto.UserDTO;
import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
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
        fee.setFeeName(feeWithDeadlineDTO.getFeeName());
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

//    public ResponseEntity<?> updateInfo(String username, UserDTO userDTO) {
//
//        Optional<User> userDB = userRepository.findByUsername(username);
//
//        if(userDB.isPresent()) {
//            User user = userDB.get();
//            if(userDTO.getPhoneNumber() != null ) {user.setPhoneNumber(userDTO.getPhoneNumber());}
//            if(userDTO.getBankName() != null ) {user.setBankName(userDTO.getBankName());}
//            if(userDTO.getFullname() != null ) {user.setName(userDTO.getFullname());}
//            if(userDTO.getBankNumber() != null ) {user.setBankAccountNumber(userDTO.getBankNumber());}
//
//            userRepository.save(user);
//            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Cập nhật thông tin thành công"));
//        } else {
//            return ResponseEntity.badRequest().body("Cập nhât thông tin không thành công");
//        }
//    }
    @Override
    public void deleteFeeWithDeadline(Integer id) {
        feeWithDeadlineRepository.deleteById(id);
    }

    @Override
    public List<FeeWithDeadline> getAllFeeWithDeadline(Integer roomId) {
        return feeWithDeadlineRepository.findAllByRoomId(roomId);
    }

    @Override
    public FeeWithDeadline updateFeeWithDeadline(Integer id, FeeWithDeadlineDTO feeWithDeadlineDTO) {

        Optional<FeeWithDeadline> feeWithDeadlineDB = feeWithDeadlineRepository.findById(id);

        if (feeWithDeadlineDB.isPresent()) {
            FeeWithDeadline fee = feeWithDeadlineDB.get();
            if (feeWithDeadlineDTO.getStatus() != null) {
                fee.setStatus(feeWithDeadlineDTO.getStatus());
            }
            if (feeWithDeadlineDTO.getDeadline() != null) {
                fee.setDeadline(feeWithDeadlineDTO.getDeadline());
            }
            if(feeWithDeadlineDTO.getFeeName() != null) {
                fee.setFeeName(feeWithDeadlineDTO.getFeeName());
            }
            if(feeWithDeadlineDTO.getPrice() != null) {
                fee.setMoney(feeWithDeadlineDTO.getPrice());
            }
            if (feeWithDeadlineDTO.getRoomId() != null) {
                fee.setRoomId(feeWithDeadlineDTO.getRoomId());
            }

            feeWithDeadlineRepository.save(fee);
            return fee;
        } else {
            return null;
        }
    }

    @Override
    public List<FeeWDStatusDTO> userStatusFeeWD(Integer roomId, String username){
        Integer userId = feeWithDeadlineRepository.convertUsernameToUserId(username);

        BigDecimal pricePerUser = feeWithDeadlineRepository.pricePerUser(userId, roomId);

        List<FeeWDStatusDTO> feeWDStatusDTOList = feeWithDeadlineRepository.getFeeInfo(roomId, userId, pricePerUser);

        return feeWDStatusDTOList;

    }

}
