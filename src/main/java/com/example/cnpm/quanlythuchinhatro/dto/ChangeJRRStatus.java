package com.example.cnpm.quanlythuchinhatro.dto;

public class ChangeJRRStatus {

    private Integer roomId;
    private Integer userId;
    private Boolean accept;

    public ChangeJRRStatus() {
    }
    public ChangeJRRStatus(Integer userId, Integer roomId, Boolean accept) {
        this.userId = userId;
        this.roomId = roomId;
        this.accept = accept;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomId() {
        return this.roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Boolean getAccept() {
        return this.accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }
}