package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.model.MemberOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberOfRoomRepository extends JpaRepository<MemberOfRoom, Integer> {
    @Query("SELECT u.id AS id, u.name AS fullName, u.phoneNumber AS phoneNumber, u.bankName AS bankName, u.bankAccountNumber AS bankNumber, u.avatarUrl AS avatarUrl FROM MemberOfRoom mr JOIN User u ON mr.userId = u.id WHERE mr.roomId =:roomId")
    List<Object[]> getAllMemberOfRoom(Integer roomId);

    Optional<MemberOfRoom> findByUserId(Integer userId);
    boolean existsByUserIdAndRoomIdAndStatus(Integer userId, Integer roomId, Integer status);
}