package com.sbs.example.practice.service;

import java.util.List;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dao.ArticleDao;
import com.sbs.example.practice.dto.Article;
import com.sbs.example.practice.dto.ArticleReply;
import com.sbs.example.practice.dto.Board;

public class ArticleService {
	private ArticleDao articleDao;
	public ArticleService() {
		articleDao=Container.articleDao;
	}
	
	// 게시판 생성
	public int makeBoard(String name,String code) {
		return articleDao.makeBoard(name,code);
	}
	
	//(게시판 선택) 현재 선택된 boardId로 게시판 불러오기
	public Board getBoardById(int boardId) {
		return articleDao.getBoardById(boardId);
	}
	
	// 게시물 작성
	public int write(int boardId, String title, String body, String name, int memberNum) {
		return articleDao.write(boardId,title,body,name,memberNum);
	}
	
	// 게시물 리스트 (사용중)
	public List<Article> getArticlesByBoardId(int boardId) {
		return articleDao.getArticlesByBoardId(boardId);
	}
	
	// 게시물 리스트 (미사용)
	public List<Article> getArticles() {
		return articleDao.getArticles();
	}
	
	// (게시판 생성) 게시판 "이름" 중복 체크
	public boolean duplicatedName(String name) {
		return articleDao.duplicatedName(name);
	}

	// (게시판 생성) 게시판 "코드" 중복 체크
	public boolean duplicatedCode(String code) {
		return articleDao.duplicatedCode(code);
	}
	
	// (게시판 선택) 게시판 목록 나타내기 + 게시물수
	public List<Board> getForPrintBoards() {
		return articleDao.getForPrintBoards();
	}
	
	// (게시판 선택) "code" 로 board 불러오기
	public Board setBoardByCode(String code) {
		return articleDao.setBoardByCode(code);
	}
	
	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}
	
	public void modifyArticle(int id, String title, String body) {
		articleDao.modifyArticle(id,title,body);
	}
	
	public void deleteOneArticle(int id) {
		articleDao.deleteOneArticle(id);
	}
	
	public void writeReply(int inputedArticleId, String reply, int loginedMemberId, String name, int boardId) {
		articleDao.writeReply(inputedArticleId,reply,loginedMemberId,name,boardId);
	}
	
	// 댓글 불러오기
	public List<ArticleReply> getForPrintArticleReply(int inputedId) {
		return articleDao.getForPrintArticleReply(inputedId);
	}
	
	public void recommand(int memberId, int id) {
		articleDao.recommand(memberId, id);
	}
}
