package com.sbs.example.practice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.practice.dao.ArticleDao;
import com.sbs.example.practice.dto.Article;
import com.sbs.example.practice.dto.Board;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = new ArticleDao();
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public int delete(int id) {
		return articleDao.delete(id);
	}

	public int write(int boardId, int memberId, String title, String body) {
		return articleDao.add(boardId, memberId, title, body);
	}

	public int modify(int id, String title, String body) {
		Map<String, Object> modifyArgs = new HashMap<>();
		modifyArgs.put("id", id);
		modifyArgs.put("title", title);
		modifyArgs.put("body", body);
		
		return modify(modifyArgs);
	}

	public List<Article> getForPrintArticles(int boardId) {
		return articleDao.getForPrintArticles(boardId);
	}
	
	public List<Article> getForPrintArticles() {
		return articleDao.getForPrintArticles(0);
	}

	public Board getBoardByCode(String boardCode) {
		return articleDao.getBoardByCode(boardCode);
	}

	public boolean isMakeBoardAvailableName(String name) {
		Board board = articleDao.getBoardByName(name);

		return board == null;
	}

	public boolean isMakeBoardAvailableCode(String code) {
		Board board = articleDao.getBoardByCode(code);

		return board == null;
	}

	public int makeBoard(String code, String name) {
		return articleDao.makeBoard(code, name);
	}

	public List<Board> getForPrintBoards() {
		return articleDao.getForPrintBoards();
	}

	public int getArticlesCount(int boardId) {
		return articleDao.getArticlesCount(boardId);
	}

	public int modify(Map<String, Object> args) {
		return articleDao.modify(args);
	}

	public void updatePageHits() {
		articleDao.updatePageHits();
	}
}
