package com.sbs.example.practice.service;

import java.util.List;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dao.MemberDao;
import com.sbs.example.practice.dto.Member;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	// 회원 추가
	public void join(String userId, String passwd, String name) {
		memberDao.add(userId, passwd, name);
	}

	// 가입 시 아이디 중복 검사
	public boolean beDuplicate(String userId) {
		return memberDao.examinId(userId);
	}

	// 로그인 시 아이디 존재 여부 체크
	public Member getMemberByUserId(String userId) {
		return memberDao.getMemberByUserId(userId);
	}

	// 회원 번호받아서 정보불러오기
	public Member getMemberById(int id) {
		return memberDao.getMemberById(id);
	}

	public String getMemberNameById(int memberId) {
		return memberDao.getMemberNameById(memberId);
	}
	
	//전멤버 다 불러오기 (여러번 DB와 접촉하지않고 한번만 접촉하려고..)
	public List<Member> getMembers() {
		return memberDao.getMembers();
	}
}
