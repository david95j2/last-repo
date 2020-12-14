package com.sbs.example.practice.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dto.Article;
import com.sbs.example.practice.dto.ArticleReply;
import com.sbs.example.practice.dto.Board;
import com.sbs.example.practice.dto.Member;
import com.sbs.example.practice.service.ArticleService;
import com.sbs.example.practice.service.MemberService;

public class ArticleController extends Controller {
	private ArticleService articleService;
	private MemberService memberService;
	private Scanner sc;

	public ArticleController() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		sc = Container.sc;
	}

	public void run(String cmd) {
		if (cmd.equals("article makeBoard")) {
			doMakeBoard(cmd);
		} else if (cmd.equals("article selectBoard")) {
			doSelectBoard(cmd);
		} else if (cmd.equals("article add")) {
			doWrite(cmd);
		} else if (cmd.startsWith("article list ")) {
			showList(cmd);
		} else if (cmd.startsWith("article detail ")) {
			showDetail(cmd);
		} else if (cmd.startsWith("article modify ")) {
			doModify(cmd);
		} else if (cmd.startsWith("article delete ")) {
			doDelete(cmd);
		} else if (cmd.startsWith("article writeReply ")) {
			doWriteReply(cmd);
		} else if (cmd.startsWith("article modifyReply ")) {
			doModifyReply(cmd);
		} else if (cmd.startsWith("article deleteReply ")) {
			doDeleteReply(cmd);
		} else if (cmd.startsWith("article recommand ")) {
			doRecommand(cmd);
		} else if (cmd.startsWith("article test ")) {
			test(cmd);
		} 
	}
	// 테스트
	private void test(String cmd) {
		
	}

	// 게시판 생성
	private void doMakeBoard(String cmd) {
		if (Container.session.logout()) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		int id = Container.session.getLoginedMemberId();
		Member member = memberService.getMemberById(id);
		if (member.isAdmin() == false) {
			System.out.println("권한이 없습니다.");
			return;
		}
		System.out.println("== 게시판 생성 ==");
		String name = "";
		String code = "";

		int nameCnt = 0;
		int nameMaxCnt = 3;
		boolean checkName = false;
		while (true) {
			if (nameCnt >= nameMaxCnt) {
				System.out.println("생성을 취소합니다.");
				break;
			}
			System.out.printf("Board Name : ");
			name = sc.nextLine().trim();
			if (name.length() == 0) {
				System.out.println("이름을 입력하세요.");
				nameCnt++;
				continue;
			}
			if (articleService.duplicatedName(name) == true) {
				System.out.println("이미 사용중인 게시판 이름");
				nameCnt++;
				continue;
			}
			checkName = true;
			break;
		}
		if (checkName == false) {
			return;
		}

		int codeCnt = 0;
		int codeMaxCnt = 3;
		boolean checkCode = false;
		while (true) {

			if (codeCnt >= codeMaxCnt) {
				System.out.println("생성을 취소합니다.");
				break;
			}
			System.out.printf("Code : ");
			code = sc.nextLine().trim();
			if (code.length() == 0) {
				System.out.println("코드를 입력하세요.");
				codeCnt++;
				continue;
			}
			if (articleService.duplicatedCode(code) == true) {
				System.out.println("이미 사용중인 코드");
				codeCnt++;
				continue;
			}
			checkCode = true;
			break;
		}
		if (checkCode == false) {
			return;
		}
		int num = articleService.makeBoard(name, code);
		System.out.printf("%d번째 게시판이 생성되었습니다.\n", num);
	}

	// 글 작성하기
	private void doWrite(String cmd) {
		if (Container.session.logout()) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		int id = Container.session.getBoardId();
		Board board = articleService.getBoardById(id);

		int session = Container.session.getLoginedMemberId();
		Member member = memberService.getMemberById(session);

		System.out.printf("\n[알림!] : 현재 [%s] 게시판 모드입니다\n", board.name);
		System.out.printf("\n== %s 게시판 글 작성 ==\n", board.name);
		String title = "";
		String body = "";
		int titleCnt = 0;
		int titleMaxCnt = 3;
		boolean checkTitle = false;
		while (true) {
			if (titleCnt >= titleMaxCnt) {
				System.out.println("게시물 작성을 취소합니다");
				break;
			}
			System.out.printf("Title : ");
			title = sc.nextLine().trim();
			if (title.length() == 0) {
				System.out.println("제목을 입력하세요.");
				titleCnt++;
				continue;
			}
			checkTitle = true;
			break;
		}
		if (checkTitle == false) {
			return;
		}
		int bodyCnt = 0;
		int bodyMaxCnt = 3;
		boolean checkBody = false;
		while (true) {
			if (bodyCnt >= bodyMaxCnt) {
				System.out.println("게시물 작성을 취소합니다");
				break;
			}
			System.out.printf("Body : ");
			body = sc.nextLine().trim();
			if (body.length() == 0) {
				System.out.println("제목을 입력하세요.");
				bodyCnt++;
				continue;
			}
			checkBody = true;
			break;
		}
		if (checkBody == false) {
			return;
		}
		int num = articleService.write(board.id, title, body, member.name,member.id);
		System.out.printf("%d번 글이 작성되었습니다.\n", num);
	}

	// 댓글 작성하기
	private void doWriteReply(String cmd) {
		int inputedArticleId = 1;
		try {
			inputedArticleId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (NumberFormatException e) {
			System.out.println("[오류메세지] : 게시물 번호는 숫자로만 입력");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("[오류메세지] : 번호를 입력");
			return;
		}
		System.out.printf("== %d번 게시물 댓글 작성==\n", inputedArticleId);
		Article article = articleService.getArticle(inputedArticleId);
		if (article == null) {
			System.out.println("게시물 없음");
			return;
		}
		// 선택한 게시물을 작성한 회원의 정보
		Member member = memberService.getMemberById(article.memberId);

		// 로그인한 회원의 회원정보
		int loginedUserId = Container.session.getLoginedMemberId();
		Member loginMember = memberService.getMemberById(loginedUserId);

		// 선택한 게시물의 게시판 id
		int boardId = article.boardId;

		System.out.printf("등록일 : %s\n", article.regDate);
		System.out.printf("작성자 : %s\n", member.name);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
		System.out.printf(" reply → ");
		String body = sc.nextLine();

		if (Container.session.logout()) {
			String joneDoe = "익명";
			int loginMemberId = 1;
			articleService.writeReply(inputedArticleId, body, loginMemberId, joneDoe, boardId);
		} else {
			articleService.writeReply(inputedArticleId, body, loginMember.id, loginMember.name, boardId);
		}
		if (Container.session.logout()) {
			System.out.println("익명으로 댓글을 등록합니다.");
		} else {
			System.out.printf("%s님의 댓글이 등록되었습니다.\n", loginMember.name);
		}
	}
	
	// 게시물 수정하기
	private void doModify(String cmd) {
		if (Container.session.logout()) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		int inputedId = 1;
		try {
			inputedId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (NumberFormatException e) {
			System.out.println("[오류메세지] : 게시물 번호는 숫자로만 입력");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("[오류메세지] : 번호를 입력");
			return;
		}
		System.out.printf("== %d번 게시물 수정 ==\n", inputedId);
		Article article = articleService.getArticle(inputedId);
		if (article == null) {
			System.out.println("게시물 없음");
			return;
		}
		int session = Container.session.getLoginedMemberId();
		if (session != article.memberId) {
			System.out.println("다른 사용자의 게시물입니다.");
			return;
		}
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		articleService.modifyArticle(inputedId, title, body);
		System.out.printf("%d번 게시물이 수정되었습니다.\n", inputedId);
	}
	
	// 게시물 삭제하기
	private void doDelete(String cmd) {
		if (Container.session.logout()) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		int inputedId = 1;
		try {
			inputedId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (NumberFormatException e) {
			System.out.println("[오류메세지] : 게시물 번호는 숫자로만 입력");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("[오류메세지] : 번호를 입력");
			return;
		}
		Article article = articleService.getArticle(inputedId);
		if (article == null) {
			System.out.println("게시물 없음");
			return;
		}
		int session = Container.session.getLoginedMemberId();
		if (session != article.memberId) {
			System.out.println("다른 사용자의 게시물입니다.");
			return;
		}
		System.out.printf("== %d번 게시물 삭제 ==\n", inputedId);
		articleService.deleteOneArticle(inputedId);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", inputedId);
	}
	
	// 글 추천 하기
	private void doRecommand(String cmd) {
		if (Container.session.logout()) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		int inputedId = 1;
		try {
			inputedId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (NumberFormatException e) {
			System.out.println("[오류메세지] : 게시물 번호는 숫자로만 입력");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("[오류메세지] : 번호를 입력");
			return;
		}

		Article article = articleService.getArticle(inputedId);

		if (article == null) {
			System.out.println("게시물 없음");
			return;
		}
		System.out.println("== 게시물 추천 ==");
		int memberId = Container.session.getLoginedMemberId();
		articleService.recommand(memberId, inputedId);
		System.out.printf("%d번 게시물을 추천합니다.", inputedId);
	}
	
	// 댓글 수정 하기
	private void doModifyReply(String cmd) {

	}
	
	// 댓글 삭제 하기
	private void doDeleteReply(String cmd) {
		// TODO Auto-generated method stub

	}

	// 게시판 불러오기
	private void doSelectBoard(String cmd) {
		System.out.println("\t  == 게시판 목록 ==");
		System.out.println("번호 / 등록날짜 / 코드 / 이름 / 게시물 수");

		List<Board> boards = articleService.getForPrintBoards();
		Board selctBoard = null;

		for (Board board : boards) {
			System.out.printf("%d / %s / %s / %s / %d\n", board.id, board.regDate, board.code, board.name,
					board.extra__aboardCnt);
		}
		int codecnt = 0;
		int codeMaxCnt = 3;
		boolean checkCode = false;
		while (true) {
			if (codecnt >= codeMaxCnt) {
				System.out.println("코드 선택을 취소합니다");
				break;
			}
			System.out.printf("Input code ! → ");
			String code = sc.nextLine().trim();
			if (code.length() == 0) {
				System.out.println("코드를 입력하세요.");
				codecnt++;
				continue;
			}
			selctBoard = articleService.setBoardByCode(code);
			if (selctBoard == null) {
				System.out.println("코드를 잘못 입력하셨습니다.");
				codecnt++;
				continue;
			}
			checkCode = true;
			break;
		}
		if (checkCode == false) {
			return;
		}
		System.out.printf("%s 게시판이 선택되었습니다.\n", selctBoard.name);
		Container.session.setSelectBoard(selctBoard.id);
	}
	
	// 게시물 불러오기
	private void showDetail(String cmd) {
		int inputedId = 1;

		try {
			inputedId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (NumberFormatException e) {
			System.out.println("[오류메세지] : 게시물 번호는 숫자로만 입력");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("[오류메세지] : 번호를 입력");
			return;
		}

		System.out.printf("== %d번 게시물 상세보기 ==\n", inputedId);

		Article article = articleService.getArticle(inputedId);

		List<ArticleReply> replys = articleService.getForPrintArticleReply(inputedId);

		if (article == null) {
			System.out.println("게시물 없음");
			return;
		}

		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성자 : %s\n", article.extra__name);
		System.out.printf("등록일 : %s\n", article.regDate);
		System.out.printf("수정일 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);

		if (replys == null) {
			System.out.println("--- 댓글 ---");
			System.out.println("댓글 없음");
			return;
		} else {
			System.out.println("--- 댓글 ---");
			for (ArticleReply reply : replys) {
				System.out.printf("[%s] : %s\n", reply.extra__name, reply.reply);
			}
		}
	}
	
	// 게시물 리스트
	private void showList(String cmd) {
		int page = 1;
		try {
			page = Integer.parseInt(cmd.split(" ")[2]);
		} catch (NumberFormatException e) {
			System.out.println("[오류] : 페이지번호는 숫자로만 입력하세요.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			page = 1;
		}
		if (page <= 1) {
			page = 1;
		}
		int id = Container.session.getBoardId();
		Board board = articleService.getBoardById(id);
		
		List<Article> articles = articleService.getArticlesByBoardId(board.id);
		List<Member> members = Container.memberService.getMembers();
		
		int pageList = 10;
		int startPage = articles.size() - 1;
		startPage -= (page - 1) * pageList;
		int endPage = startPage - (pageList - 1);
		if (endPage <= 0) {
			endPage = 0;
		}
		if (startPage < endPage) {
			System.out.println("작성된 글이 없습니다.");
			return;
		}
		System.out.printf("== %s 게시판 ==\n", board.name);
		System.out.println("번호 / 날짜 / 수정날짜 / 작성자 / 제목");
		
		for (int i = startPage; i >= endPage; i--) {
			Article article = articles.get(i);
			Member member = members.get(article.memberId-1);
			System.out.printf("%.0f / %s / %s / %s / %s\n", article.extra__rownum,article.regDate,article.updateDate, member.name, article.title);
		}
	}
}
