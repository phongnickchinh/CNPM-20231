package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.dto.FeeWDStatusDTO;
import com.example.cnpm.quanlythuchinhatro.dto.FeeWithDeadlineDTO;
import com.example.cnpm.quanlythuchinhatro.dto.ListFeeWDDTO;
import com.example.cnpm.quanlythuchinhatro.dto.StatusFeeWDRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.service.FeeWithDeadlineService;
import com.example.cnpm.quanlythuchinhatro.service.UserFeeWithDeadlineService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/feewd")
public class FeeWithDeadlineController {
    private final FeeWithDeadlineService feeWithDeadlineService;

    private final UserFeeWithDeadlineService userFeeWithDeadlineService;

    public FeeWithDeadlineController(FeeWithDeadlineService feeWithDeadlineService, UserFeeWithDeadlineService userFeeWithDeadlineService) {
        this.feeWithDeadlineService = feeWithDeadlineService;
        this.userFeeWithDeadlineService = userFeeWithDeadlineService;
    }

    @PostMapping("/create")
    public ResponseEntity<?>  create(@RequestBody FeeWithDeadlineDTO fee) {
        feeWithDeadlineService.createFeeWithDeadline(fee);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Tạo chi phí có thời hạn thành công"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        feeWithDeadlineService.deleteFeeWithDeadline(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Delete a fee with deadline"));
    }
    @GetMapping("/getByRoomId/{roomId}")
    public ResponseEntity<List<ListFeeWDDTO>> getAllFeeWithDeadline(@PathVariable("roomId") Integer roomId) {
        List<ListFeeWDDTO> list = feeWithDeadlineService.listFeeWDByRoomId(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?>  updateFeeWithDeadline(@PathVariable("id") Integer id, @RequestBody ListFeeWDDTO feeWithDeadline) {
        FeeWithDeadline fee = feeWithDeadlineService.updateFeeWithDeadline(id, feeWithDeadline);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Cập nhật thành công"));
    }

    @PatchMapping("/reverseStatus/{userId}/{feeId}")
    public ResponseEntity<UserFeeWithDeadline> reverseStatus(@PathVariable Integer userId, @PathVariable Integer feeId) {
        UserFeeWithDeadline userFee = userFeeWithDeadlineService.reverseStatus(userId, feeId);

        if (userFee != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userFee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/userStatusFeeWD")
    public ResponseEntity<?> getUserStatus(@RequestParam Integer roomId, HttpSession httpSession) {
        Object username = httpSession.getAttribute("loggedInUser");
        List<Map<String,Object>> feeStatusList = feeWithDeadlineService.userStatusFeeWD(roomId, username.toString());
        return ResponseEntity.ok(feeStatusList);
    }

    @GetMapping("/roomStatusFeeWD")
    public ResponseEntity<?> getRoomStatusFeeWD(@RequestParam Integer roomId) {
        return ResponseEntity.ok(feeWithDeadlineService.roomStatusFeeWD(roomId));
    }
}
