package com.example.cnpm.quanlythuchinhatro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cnpm.quanlythuchinhatro.model.MemberOfRoom;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberOfRoomRepository extends JpaRepository<MemberOfRoom, Integer> {
    Optional<MemberOfRoom> findByUserId(Integer userId);
    boolean existsByUserIdAndRoomIdAndStatus(Integer userId, Integer roomId, Integer status);

}
