package com.sbs.example.practice.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlUtil.MysqlUtil;
import com.sbs.example.mysqlUtil.SecSql;
import com.sbs.example.practice.dto.Member;

public class MemberDao {

	public MemberDao() {
		MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "a2");
		MysqlUtil.setDevMode(false);
	}

	// 회원 추가 (회원가입시 사용중)
	public void add(String userId, String passwd, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member` SET regDate = NOW()");
		sql.append(", userId = ?", userId);
		sql.append(", passwd = ?", passwd);
		sql.append(", `name` = ?", name);

		MysqlUtil.insert(sql);
	}

	// 아이디 중복 체크 (회원가입시 사용중)
	public boolean examinId(String userId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `member` WHERE userId = ?", userId);
		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

		if (memberMap.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	// 아이디 존재 여부 (로그인시 사용중)
	public Member getMemberByUserId(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `member` WHERE userId = ?", loginId);
		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

		if (memberMap.isEmpty()) {
			return null;
		} 
		int userNum = (int)memberMap.get("id");
		String userRegDate = (String)memberMap.get("regDate");
		String userId = (String)memberMap.get("userId");
		String userPwd = (String)memberMap.get("passwd");
		String userName = (String)memberMap.get("name");
		Member member = new Member(userNum,userRegDate,userId,userPwd,userName);
		
		return member;
	}

	// 회원 번호받아서 정보불러오기
	public Member getMemberById(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `member` WHERE id = ?", id);
		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

		if (memberMap.isEmpty()) {
			return null;
		} 
		int userNum = (int)memberMap.get("id");
		String userRegDate = (String)memberMap.get("regDate");
		String userId = (String)memberMap.get("userId");
		String userPwd = (String)memberMap.get("passwd");
		String userName = (String)memberMap.get("name");
		Member member = new Member(userNum,userRegDate,userId,userPwd,userName);
		
		return member;
	}
	
	//이름만 받을때 사용
	public String getMemberNameById(int id) {
		
		SecSql sql = new SecSql();
		sql.append("SELECT `name` FROM `MEMBER` WHERE id = ?",id);
		
		String name = MysqlUtil.selectRowStringValue(sql);
		if (name==null) {
			return "";
		}
		return name;
	}
	
	//전멤버 다 불러오기 (여러번 DB와 접촉하지않고 한번만 접촉하려고..)
	public List<Member> getMembers() {
		List<Member> members = new ArrayList<>();
		
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM `member`");
		List<Map<String, Object>> memberMaps = MysqlUtil.selectRows(sql);

		if (memberMaps.isEmpty()) {
			return null;
		} 
		for(Map<String,Object> memberMap : memberMaps) {
			Member member = new Member();
			member.id = (int)memberMap.get("id");
			member.regDate = (String)memberMap.get("regDate");
			member.userId = (String)memberMap.get("userId");
			member.passwd = (String)memberMap.get("passwd");
			member.name = (String)memberMap.get("name");
			members.add(member);
		}
		return members;
	}
}
