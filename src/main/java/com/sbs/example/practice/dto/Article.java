package com.sbs.example.practice.dto;

import java.util.Map;

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
	public String extra__boardName;
	public String extra__boardCode;

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
	
	public Article(Map<String, Object> map) {
		this.id = (int) map.get("id");
		this.regDate = (String) map.get("regDate");
		this.updateDate = (String) map.get("updateDate");
		this.title = (String) map.get("title");
		this.body = (String) map.get("body");
		this.memberId = (int) map.get("memberId");
		this.boardId = (int) map.get("boardId");
		if (map.containsKey("extra__name")) {
			this.extra__name = (String) map.get("extra__name");
		}

		if (map.containsKey("extra__boardName")) {
			this.extra__boardName = (String) map.get("extra__boardName");
		}

		if (map.containsKey("extra__boardCode")) {
			this.extra__boardCode = (String) map.get("extra__boardCode");
		}
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", title=" + title
				+ ", body=" + body + ", memberId=" + memberId + ", boardId=" + boardId + ", hit=" + hit + ", like="
				+ like + ", extra__rownum=" + extra__rownum + ", extra__name=" + extra__name + ", extra__boardName="
				+ extra__boardName + ", extra__boardCode=" + extra__boardCode + "]";
	}

	
}
