package com.sbs.example.practice.session;

public class Session {
	private int loginedMemberId;
	private int selectedBoardId;
	
	public Session() {
		loginedMemberId=0;
		// 자유 게시판을 기본 선택으로 설정한다.
		selectedBoardId=2;
	}
	// 로그인
		public boolean login() {
			return loginedMemberId != 0;
		}
		
		// 로그아웃
		public boolean logout()	{
			return !login();
		}
		
		// 로그인 넘버값 세팅
		public void setLogInId(int id) {
			loginedMemberId=id;
		}
		
		// 로그인 넘버 받기
		public int getLoginedMemberId() {
			return loginedMemberId;
		}
		
		// 게시판 넘버값 세팅
		public void setSelectBoard(int id) {
			selectedBoardId=id;
		}
		
		// 게시판 넘버값 받기
		public int getBoardId() {
			return selectedBoardId;
		}
}
