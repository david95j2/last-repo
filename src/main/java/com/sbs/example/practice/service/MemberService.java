package com.sbs.example.practice.service;

import com.sbs.example.practice.dao.MemberDao;
import com.sbs.example.practice.dto.Member;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		memberDao = new MemberDao();
	}

	public int join(String loginId, String loginPw, String name) {
		return memberDao.add(loginId, loginPw, name);
	}

	public Member getMemberById(int id) {
		return memberDao.getMemberById(id);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

}
