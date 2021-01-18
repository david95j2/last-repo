package com.sbs.example.practice.controller;

import java.util.Scanner;

import com.sbs.example.practice.Container;
import com.sbs.example.practice.dto.Member;
import com.sbs.example.practice.service.MemberService;

public class MemberController extends Controller {

	private MemberService memberService;

	public MemberController() {
		memberService = Container.memberService;
	}

	public void doCommand(String cmd) {
		if (cmd.startsWith("member join")) {
			doJoin(cmd);
		} else if (cmd.startsWith("member login")) {
			doLogin(cmd);
		} else if (cmd.startsWith("member logout")) {
			doLogout(cmd);
		} else if (cmd.startsWith("member whoami")) {
			showWhoami(cmd);
		}
	}

	private void showWhoami(String cmd) {
		System.out.println("== 회원확인 ==");
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		int loginedMemberId = Container.session.getLoginedMemberId();
		Member member = memberService.getMemberById(loginedMemberId);
		System.out.printf("번호 : %d\n", member.getId());
		System.out.printf("가입날자 : %s\n", member.getRegDate());
		System.out.printf("로그인아이디 : %s\n", member.getLoginId());
		System.out.printf("이름 : %s\n", member.getName());
		System.out.printf("회원종류 : %s\n", member.getType());
	}

	private void doLogout(String cmd) {
		System.out.println("== 로그아웃 ==");

		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		System.out.println("로그아웃 되었습니다.");
		Container.session.logout();
	}

	private void doJoin(String cmd) {
		if (Container.session.isLogined()) {
			System.out.println("로그아웃 후 이용해주세요.");
			return;
		}

		System.out.println("== 회원가입 ==");

		Scanner sc = Container.scanner;

		System.out.printf("로그인아이디 : ");
		String loginId = sc.nextLine().trim();

		if (loginId.length() == 0) {
			System.out.println("로그인아이디를 입력해주세요.");
			return;
		}

		System.out.printf("로그인비밀번호 : ");
		String loginPw = sc.nextLine().trim();

		if (loginPw.length() == 0) {
			System.out.println("로그인비밀번호를 입력해주세요.");
			return;
		}

		System.out.printf("로그인비밀번호확인 : ");
		String loginPwConfirm = sc.nextLine().trim();

		if (loginPwConfirm.length() == 0) {
			System.out.println("로그인비밀번호확인을 입력해주세요.");
			return;
		}

		if (loginPw.equals(loginPwConfirm) == false) {
			System.out.println("로그인비밀번호가 일치하지 않습니다.");
			return;
		}

		System.out.printf("이름 : ");
		String name = sc.nextLine().trim();

		if (name.length() == 0) {
			System.out.println("이름을 입력해주세요.");
			return;
		}

		int id = memberService.join(loginId, loginPw, name);

		System.out.printf("%d번 회원이 생성되었습니다.\n", id);
	}

	private void doLogin(String cmd) {
		System.out.println("== 로그인 ==");

		if (Container.session.isLogined()) {
			System.out.println("로그아웃 후 이용해주세요.");
			return;
		}

		Scanner sc = Container.scanner;

		System.out.printf("로그인아이디 : ");
		String loginId = sc.nextLine().trim();

		if (loginId.length() == 0) {
			System.out.println("로그인아이디를 입력해주세요.");
			return;
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			System.out.println("존재하지 않는 회원입니다.");
			return;
		}

		System.out.printf("로그인비밀번호 : ");
		String loginPw = sc.nextLine().trim();

		if (loginPw.length() == 0) {
			System.out.println("로그인비밀번호를 입력해주세요.");
			return;
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}

		Container.session.login(member.getId());

		System.out.printf("%s님 환영합니다.\n", member.getName());
	}
}
