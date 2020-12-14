package com.sbs.example.practice.service;

import java.util.Collections;
import java.util.List;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dto.Article;
import com.sbs.example.practice.dto.Board;
import com.sbs.example.practice.dto.Member;
import com.sbs.example.practice.util.Util;

public class BuildService {

	private ArticleService articleService;
	private MemberService memberService;

	public BuildService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void buildSite() {
		System.out.println("site/article 폴더 생성");
		Util.rmdir("site");
		Util.mkdirs("site");

		Util.copy("site_template/app.css", "site/app.css");

		buildIndexPage();
		buildArticleDetailPages();
	}

	private void buildIndexPage() {
		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("index");
		String foot = Util.getFileContents("site_template/foot.html");
		
		String mainHtml = Util.getFileContents("site_template/index.html");
		
		
		sb.append(head);
		sb.append(mainHtml);
		sb.append(foot);

		String filePath = "site/index.html";
		Util.writeFile(filePath, sb.toString());
		System.out.println(filePath + " 생성");
	}

	private void buildArticleDetailPages() {
		List<Article> articles = articleService.getArticles();

		String head = getHeadHtml("article_detail");
		String foot = Util.getFileContents("site_template/foot.html");

		// 게시물 상세페이지 생성
		for (Article article : articles) {
			StringBuilder sb = new StringBuilder();

			sb.append(head);

			sb.append("번호 : " + article.id + "<br>");
			sb.append("작성날짜 : " + article.regDate + "<br>");
			sb.append("갱신날짜 : " + article.updateDate + "<br>");
			sb.append("제목 : " + article.title + "<br>");
			sb.append("내용 : " + article.body + "<br>");
			sb.append("<a href=\"article_detail_" + (article.id - 1) + ".html\">이전글</a><br>");
			sb.append("<a href=\"article_detail_" + (article.id + 1) + ".html\">다음글</a><br>");

			sb.append("</div>");

			sb.append(foot);

			String fileName = "article_detail_" + article.id + ".html";

			String filePath = "site/" + fileName;

			Util.writeFile(filePath, sb.toString());
			System.out.println(filePath + " 생성");
		}
	}

	private String getHeadHtml(String pageName) {
		String head = Util.getFileContents("site_template/head.html");

		StringBuilder boardMenuContentHtml = new StringBuilder();
		List<Board> forPrintBoards = articleService.getForPrintBoards();

		for (Board board : forPrintBoards) {
			boardMenuContentHtml.append("<li>");

			String link = board.code + "-list-1.html";

			boardMenuContentHtml.append("<a href=\"" + link + "\" class=\"block\">");

			boardMenuContentHtml.append(getTitleBarContentByPageName("article_list_" + board.code));

			boardMenuContentHtml.append("</a>");

			boardMenuContentHtml.append("</li>");
		}

		head = head.replace("${menu-bar__menu-1__board-menu-content}", boardMenuContentHtml.toString());

		String titleBarContentHtml = getTitleBarContentByPageName(pageName);

		head = head.replace("${title-bar__content}", titleBarContentHtml);

		return head;
	}

	private String getTitleBarContentByPageName(String pageName) {
		if (pageName.equals("index")) {
			return "<i class=\"fas fa-home\"></i> <span>HOME</span>";
		} else if (pageName.equals("article_detail")) {
			return "<i class=\"fas fa-file-alt\"></i> <span>ARTICLE DETAIL</span>";
		} else if (pageName.startsWith("article_list_free")) {
			return "<i class=\"fab fa-free-code-camp\"></i> <span>FREE LIST</span>";
		} else if (pageName.startsWith("article_list_notice")) {
			return "<i class=\"fas fa-flag\"></i> <span>NOTICE LIST</span>";
		} else if (pageName.startsWith("article_list_")) {
			return "<i class=\"fas fa-clipboard-list\"></i> <span>NOTICE LIST</span>";
		}

		return "";
	}

	public void buildList(String code, int id) {
		System.out.println("test/article 폴더 생성");
		Util.rmdir("test");
		Util.mkdirs("test");

		Util.copy("site_template/app.css", "test/app.css");

		buildArticleList(code, id);
	}

	private void buildArticleList(String code, int id) {
		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("index");
		String foot = Util.getFileContents("site_template/foot.html");

		String mainHtml = getListByBoard("index",id);

		sb.append(head);
		sb.append(mainHtml);
		sb.append(foot);

		String filePath = "test/index.html";
		Util.writeFile(filePath, sb.toString());
		System.out.println(filePath + " 생성");
	}

	private String getListByBoard(String list, int id) {
		String head = Util.getFileContents("site_template/index.html");

		StringBuilder boardMenuContentHtml = new StringBuilder();
		List<Article> articles = articleService.getArticlesByBoardId(id);
		List<Member> members = memberService.getMembers();
		Collections.reverse(articles);
		for (Article article : articles) {
			String writerName = members.get(article.memberId-1).name;
			boardMenuContentHtml.append("<div>");
			boardMenuContentHtml.append("<div class=\"article-list__cell-id\">"+article.id+"</div>");
			boardMenuContentHtml.append("<div class=\"article-list__cell-title\">");
			boardMenuContentHtml.append("<a href=\"article_detail_"+article.id+".html\" class=\"hover_underline\">"+article.title+"</a>");
			boardMenuContentHtml.append("</div>");
			boardMenuContentHtml.append("<div class=\"article-list__cell-writer\">"+writerName+"</div>");
			boardMenuContentHtml.append("<div class=\"article-list__cell-reg-date\">"+article.regDate+"</div>");
			boardMenuContentHtml.append("<div class=\"article-list__cell-like\">"+article.like+"</div>");
			boardMenuContentHtml.append("<div class=\"article-list__cell-hit\">"+article.hit+"</div>");
			boardMenuContentHtml.append("</div>");
		}

		head = head.replace("${article-list-by-boardId}", boardMenuContentHtml.toString());

		return head;
	}

	public void test() {
		System.out.println("도달!");
	}
}
