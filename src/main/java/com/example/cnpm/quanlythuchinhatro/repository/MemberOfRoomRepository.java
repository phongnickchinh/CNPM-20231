package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.model.MemberOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MemberOfRoomRepository extends JpaRepository<MemberOfRoom, Integer> {
    @Query(value = "SELECT u.id AS id, u.name AS fullname, u.phone_number AS phoneNumber, u.bank_name AS bankName, " +
            "u.bank_account_number AS bankNumber, u.avatar_url AS avatarUrl " +
            "FROM member_of_room mr " +
            "JOIN user u ON mr.user_id = u.id " +
            "WHERE mr.room_id = 1 AND mr.status = 1 ", nativeQuery = true)
    List<Map<String, Object>> getAllMemberOfRoom(Integer roomId);

    @Query("SELECT mor.userId FROM MemberOfRoom mor WHERE mor.roomId = :roomId")
    List<Integer> findUserIdsByRoomId(@Param("roomId") Integer roomId);

    boolean existsByUserIdAndRoomIdAndStatus(Integer userId, Integer roomId, Integer status);

    @Query("SELECT mr FROM MemberOfRoom mr WHERE mr.userId =:userId AND mr.roomId =:roomId AND mr.status = 1")
    MemberOfRoom findByUserIdAndRoomId(Integer userId, Integer roomId);

    @Query("SELECT COUNT(mr) FROM MemberOfRoom mr WHERE mr.roomId =:roomId AND mr.status = 1")
    Integer countMember(Integer roomId);
}
