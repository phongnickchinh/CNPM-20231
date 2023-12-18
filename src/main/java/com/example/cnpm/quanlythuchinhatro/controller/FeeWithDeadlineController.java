package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.service.FeeWithDeadlineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feewd")
public class FeeWithDeadlineController {
    private final FeeWithDeadlineService feeWithDeadlineService;

    public FeeWithDeadlineController(FeeWithDeadlineService feeWithDeadlineService) {
        this.feeWithDeadlineService = feeWithDeadlineService;
    }
    @PostMapping("/create")
    public String create(@RequestBody FeeWithDeadline feeWithDeadline) {
        feeWithDeadlineService.createFeeWithDeadline(feeWithDeadline);
        return "Create a fee with deadline";
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        feeWithDeadlineService.deleteFeeWithDeadline(id);
        return "Delete a fee with deadline";
    }
    @GetMapping("/getByRoomId")
    public List<FeeWithDeadline> getAllFeeWithDeadline(@RequestParam("roomId") Integer roomId) {
        return feeWithDeadlineService.getAllFeeWithDeadline(roomId);
    }
    @PutMapping("/update")
    public FeeWithDeadline updateFeeWithDeadline(@RequestParam("id") Integer id, @RequestBody FeeWithDeadline feeWithDeadline) {
        return feeWithDeadlineService.updateFeeWithDeadline(id, feeWithDeadline);
    }

}
