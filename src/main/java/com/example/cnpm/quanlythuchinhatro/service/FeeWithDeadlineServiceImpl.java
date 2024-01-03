package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.FeeWithDeadlineDTO;
import com.example.cnpm.quanlythuchinhatro.dto.UserDTO;
import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.repository.FeeWithDeadlineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeeWithDeadlineServiceImpl implements FeeWithDeadlineService{
    private final FeeWithDeadlineRepository feeWithDeadlineRepository;
    public FeeWithDeadlineServiceImpl(FeeWithDeadlineRepository feeWithDeadlineRepository) {
        this.feeWithDeadlineRepository = feeWithDeadlineRepository;
    }


    @Override
    public void createFeeWithDeadline(FeeWithDeadline feeWithDeadline) {
        feeWithDeadlineRepository.save(feeWithDeadline);
    }

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
            if(feeWithDeadlineDTO.getMoney() != null) {
                fee.setFeeName(feeWithDeadlineDTO.getFeeName());
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



//    public User updateInfo(String username, UserDTO userDTO) {
//
//        Optional<User> userDB = userRepository.findByUsername(username);
//
//        if(userDB.isPresent()) {
//            User user = userDB.get();
//            if(userDTO.getPhoneNumber() != null ) {user.setPhoneNumber(userDTO.getPhoneNumber());}
//            if(userDTO.getAvatarUrl() != null ) {user.setAvatarUrl(userDTO.getAvatarUrl());}
//            if(userDTO.getBankName() != null ) {user.setBankName(userDTO.getBankName());}
//            if(userDTO.getBankAccountNumber() != null ) {user.setBankAccountNumber(userDTO.getBankAccountNumber());}
//
//            userRepository.save(user);
//            return user;
//        } else {
//            return null;
//        }
//    }

}
