package com.example.websmartspending.model;
import jakarta.persistence.Embeddable;


import java.io.Serializable;

@Embeddable
public class JoinRoomReQuestIDEmbeddable implements Serializable {

    private Integer userId;
    private Long roomId;

    // getters, setters, equals, and hashCode methods
    public JoinRoomReQuestIDEmbeddable(Integer userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
