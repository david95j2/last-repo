package com.sbs.example.practice.dto;

public class Member {

	public int id;
	public String regDate;
	public String userId;
	public String passwd;
	public String name;

	public Member() {

	}

	public Member(int id, String regDate, String userId, String passwd, String name) {
		this.id = id;
		this.regDate = regDate;
		this.userId = userId;
		this.passwd = passwd;
		this.name = name;
	}

	public String getType() {
		return isAdmin() ? "관리자" : "일반회원";
	}

	public boolean isAdmin() {
		return userId.equals("user1");
	}
}
