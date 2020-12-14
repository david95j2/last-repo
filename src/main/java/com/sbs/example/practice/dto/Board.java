package com.sbs.example.practice.dto;

public class Board {
	
	public int id;
	public String regDate;
	public String updateDate;
	public String name;
	public String code;
	
	public int extra__aboardCnt;
	
	public Board(int id, String regDate, String updateDate, String name, String code, int cnt) {
		this.id=id;
		this.regDate=regDate;
		this.updateDate=updateDate;
		this.name=name;
		this.code=code;
		this.extra__aboardCnt=cnt;
	}
}
