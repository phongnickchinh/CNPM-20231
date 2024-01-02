package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeWithDeadlineRepository extends JpaRepository<FeeWithDeadline, Integer> {
    List<FeeWithDeadline> findAllByRoomId(Integer roomId);

}
