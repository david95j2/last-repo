package com.sbs.example.practice.dto;

import java.util.Map;

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
	
	@Override
	public String toString() {
		return "Board [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", name=" + name + ", code="
				+ code + ", extra__aboardCnt=" + extra__aboardCnt + "]";
	}

	public Board(Map<String, Object> map) {
		this.id = (int) map.get("id");
		this.regDate = (String) map.get("regDate");
		this.updateDate = (String) map.get("updateDate");
		this.code = (String) map.get("code");
		this.name = (String) map.get("name");
	}
}
