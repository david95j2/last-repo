package com.sbs.example.practice.session;

public class Session {
	private int loginedMemberId;
	private String currentBoardCode;

	public Session() {
		// 공지사항을 기본 선택된 게시판으로 지정
		currentBoardCode = "notice";
	}

	public int getLoginedMemberId() {
		return loginedMemberId;
	}

	public void logout() {
		loginedMemberId = 0;
	}

	public void login(int id) {
		loginedMemberId = id;
	}

	public boolean isLogined() {
		return loginedMemberId > 0;
	}

	public String getCurrentBoardCode() {
		return currentBoardCode;
	}
	
	public void setCurrentBoardCode(String boardCode) {
		this.currentBoardCode = boardCode;
	}
}
