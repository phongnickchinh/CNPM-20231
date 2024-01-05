package com.example.cnpm.quanlythuchinhatro.dto;

public class MonthlyStatsRequest {
    private Integer roomId;

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public MonthlyStatsRequest(Integer roomId) {
		super();
		this.roomId = roomId;
	}

	public MonthlyStatsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


}
