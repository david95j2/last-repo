package com.sbs.example.practice.dto;

public class Article {
	public int id;
	public String regDate;
	public String updateDate;
	public String title;
	public String body;
	public int memberId;
	public int boardId;
	public int hit;
	public int like;
	
	public double extra__rownum;
	public String extra__name;

	public Article() {
		
	}
	
	public Article(int id, String regDate, String updateDate, String title, String body, int memberId,
			int boardId) {
		this.id=id;
		this.regDate=regDate;
		this.updateDate=updateDate;
		this.title=title;
		this.body=body;
		this.memberId=memberId;
		this.boardId=boardId;
	}
		
}
