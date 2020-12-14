package com.sbs.example.practice.controller;

import java.util.Scanner;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dto.Member;
import com.sbs.example.practice.service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;
	private Scanner sc;
	
	public MemberController() {
		memberService = Container.memberService;
		sc=Container.sc;
	}

	public void run(String cmd) {
		if (cmd.equals("member join")) {
			doJoin();
		} else if (cmd.equals("member login")) {
			doLogin();
		} else if (cmd.equals("member logout")) {
			doLogout();
		} else if (cmd.equals("member whoami")) {
			showMember();
		}
	}

	// 회원 정보 보기
	private void showMember() {
		if(Container.session.logout()) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		int id = Container.session.getLoginedMemberId();
		Member member = memberService.getMemberById(id);
		
		System.out.println("== 회원정보 ==");
		System.out.printf("회원번호 : %d\n",member.id);
		System.out.printf("가입날짜 : %s\n",member.regDate);
		System.out.printf("아이디 : %s\n",member.userId);
		System.out.printf("이름 : %s\n",member.name);
		System.out.printf("유형 : %s\n",member.getType());
	}

	// 로그아웃 기능
	private void doLogout() {
		if(Container.session.logout()) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		int id = Container.session.getLoginedMemberId();
		Member member = memberService.getMemberById(id);
		System.out.printf("[%s님] : 로그아웃되었습니다.\n",member.name);
		Container.session.setLogInId(0);
	}

	// 로그인 기능
	private void doLogin() {
		if(Container.session.login()) {
			System.out.println("로그아웃 후 이용가능");
			return;
		}
		System.out.println("== 로그인 ==");
		String userId = "";
		String passwd;
		
		Member member = null;

		int loginCnt = 0;
		int loginMaxCnt = 3;
		boolean isIdFine = false;
		while (true) {
			if (loginCnt >= loginMaxCnt) {
				System.out.println("로그인 취소");
				break;
			}
			System.out.printf("Id : ");
			userId = sc.nextLine().trim();
			if (userId.length() == 0) {
				System.out.println("아이디를 입력하세요.");
				loginCnt++;
				continue;
			}
			member = memberService.getMemberByUserId(userId);
			if (member == null) {
				System.out.println("아이디가 존재하지 않습니다.");
				loginCnt++;
				continue;
			}
			isIdFine = true;
			break;
		}
		if (isIdFine == false) {
			return;
		}

		int loginPwCnt = 0;
		int loginPwMaxCnt = 3;
		boolean isPwFine = false;
		while (true) {
			if (loginPwCnt >= loginPwMaxCnt) {
				System.out.println("로그인 취소");
				break;
			}

			System.out.printf("Pw : ");
			passwd = sc.nextLine().trim();
			if (userId.length() == 0) {
				System.out.println("아이디를 입력하세요.");
				loginCnt++;
				continue;
			}
			if (member.passwd.equals(passwd) == false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				loginPwCnt++;
				continue;
			}
			isPwFine = true;
			break;
		}
		if (isPwFine == false) {
			return;
		}

		System.out.printf("[%s님] : 로그인되었습니다.\n", member.name);
		Container.session.setLogInId(member.id);
	}

	// 회원가입 기능
	private void doJoin() {
		System.out.println("== 회원 가입 ==");
		String userId = "";
		String passwd;
		String name;
		int loginCount = 0;
		int loginMaxCount = 3;
		boolean beFineId = false;
		while (true) {
			if (loginCount >= loginMaxCount) {
				System.out.println("가입 취소");
				break;
			}
			System.out.printf("Id : ");
			userId = Container.sc.nextLine().trim();
			if (userId.length() == 0) {
				System.out.println("아이디 입력하세요");
				loginCount++;
				continue;
			}
			if (memberService.beDuplicate(userId) == true) {
				System.out.println("이미 사용중인 아이디");
				loginCount++;
				continue;
			}
			beFineId = true;
			break;
		}
		if (beFineId == false) {
			return;
		}
		while (true) {
			System.out.printf("Pw : ");
			passwd = Container.sc.nextLine().trim();
			if (passwd.length() == 0) {
				System.out.println("비밀번호 입력하세요");
				continue;
			}
			break;
		}
		while (true) {
			System.out.printf("name : ");
			name = Container.sc.nextLine().trim();
			if (name.length() == 0) {
				System.out.println("이름 입력하세요");
				continue;
			}
			break;
		}
		memberService.join(userId, passwd, name);
		System.out.printf("[%s님] : 가입되었습니다.\n", name);
	}
}
