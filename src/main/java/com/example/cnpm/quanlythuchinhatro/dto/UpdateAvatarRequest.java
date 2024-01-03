package com.example.cnpm.quanlythuchinhatro.dto;

public class UpdateAvatarRequest {
	private String avatarUrl;

	public UpdateAvatarRequest(String avatarUrl) {
		super();
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public UpdateAvatarRequest() {
	}
}
