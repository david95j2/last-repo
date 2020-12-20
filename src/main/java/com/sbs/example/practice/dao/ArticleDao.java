package com.sbs.example.practice.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlUtil.MysqlUtil;
import com.sbs.example.mysqlUtil.SecSql;
import com.sbs.example.practice.dto.Article;
import com.sbs.example.practice.dto.ArticleReply;
import com.sbs.example.practice.dto.Board;

public class ArticleDao {

	public ArticleDao() {
		MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "a2");
		MysqlUtil.setDevMode(false);
	}

	public int makeBoard(String name, String code) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO board");
		sql.append(" SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", `name` = ?", name);
		sql.append(", `code` = ?", code);

		return MysqlUtil.insert(sql);
	}

	public int write(int boardId, String title, String body, String name, int memberNum) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append(" SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", boardId = ?", boardId);
		sql.append(", memberId = ?", memberNum);
		sql.append(", title = ?", title);
		sql.append(", body = ?", body);
		sql.append(",hit=0,`like`=0");

		return MysqlUtil.insert(sql);
	}

	public List<Article> getArticlesByBoardId(int boardId) {
		List<Article> articles = new ArrayList<>();

		String sql; // SQL문을 저장할 String
		sql = "SELECT SUB.*, `name` FROM ( SELECT @ROWNUM := @ROWNUM + 1 AS ROWNUM, a.* FROM article a,";
		sql += " (SELECT @ROWNUM := 0) TMP WHERE boardId=? ORDER BY boardId ASC)SUB INNER JOIN `member` AS m ON SUB.memberId=m.id";
		sql += " ORDER BY SUB.ROWNUM DESC";

		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(new SecSql().append(sql, boardId));
		for (Map<String, Object> articleMap : articleListMap) {
			Article article = new Article();
			article.id = (int) articleMap.get("id");
			article.regDate = (String) articleMap.get("regDate");
			article.updateDate = (String) articleMap.get("updateDate");
			article.title = (String) articleMap.get("title");
			article.body = (String) articleMap.get("body");
			article.memberId = (int) articleMap.get("memberId");
			article.boardId = (int) articleMap.get("boardId");
			article.hit = (int) articleMap.get("hit");
			article.like = (int) articleMap.get("like");
			
			article.extra__rownum = (double) articleMap.get("ROWNUM");
			article.extra__name = (String) articleMap.get("name");
			articles.add(article);
		}
		return articles;
	}

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();

		String sql; // SQL문을 저장할 String
		sql = "SELECT article.*, member.name";
		sql += " FROM article INNER JOIN `member` ON article.memberId=member.id ORDER BY id DESC";

		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(new SecSql().append(sql));

		for (Map<String, Object> articleMap : articleListMap) {
			Article article = new Article();
			article.id = (int) articleMap.get("id");
			article.regDate = (String) articleMap.get("regDate");
			article.updateDate = (String) articleMap.get("updateDate");
			article.title = (String) articleMap.get("title");
			article.body = (String) articleMap.get("body");
			article.memberId = (int) articleMap.get("memberId");
			article.boardId = (int) articleMap.get("boardId");
			article.extra__name = (String) articleMap.get("name");
			articles.add(article);
		}

		return articles;
	}

	public Article getArticle(int id) {

		String sql; // SQL문을 저장할 String
		sql = "SELECT article.*, member.name";
		sql += " FROM article INNER JOIN `member` ON article.memberId=member.id";
		sql += " WHERE article.id = ?";

		Map<String, Object> articleMap = MysqlUtil.selectRow(new SecSql().append(sql, id));

		if (articleMap.isEmpty()) {
			return null;
		}
		Article article = new Article();
		article.id = (int) articleMap.get("id");
		article.regDate = (String) articleMap.get("regDate");
		article.updateDate = (String) articleMap.get("updateDate");
		article.title = (String) articleMap.get("title");
		article.body = (String) articleMap.get("body");
		article.memberId = (int) articleMap.get("memberId");
		article.boardId = (int) articleMap.get("boardId");
		article.extra__name = (String) articleMap.get("name");
		return article;
	}

	public void modifyArticle(int id, String title, String body) {
		MysqlUtil.update(new SecSql().append("UPDATE article SET title = ?, `body`=? WHERE id = ?", title, body, id));
	}

	public void deleteOneArticle(int id) {
		MysqlUtil.delete(new SecSql().append("DELETE FROM article WHRER id = ?", id));
	}

	public void writeReply(int inputedArticleId, String reply, int loginedMemberId, String name, int boardId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO articleReply");
		sql.append(" SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", articleId = ?", inputedArticleId);
		sql.append(", reply = ?", reply);
		sql.append(", memberReplyId = ?", loginedMemberId);
		sql.append(", boardReplyId = ?", boardId);

		MysqlUtil.insert(sql);
	}

	public List<ArticleReply> getForPrintArticleReply(int inputedId) {
		List<ArticleReply> articleReplys = new ArrayList<>();

		String replySql = "SELECT articleReply.*, member.name FROM articleReply";
		replySql += " INNER JOIN `member`";
		replySql += " ON articleReply.memberReplyId = member.id";
		replySql += " INNER JOIN article ON article.id = articleReply.articleId";
		replySql += " WHERE articleId = ?";
		List<Map<String, Object>> replyMaps = MysqlUtil.selectRows(new SecSql().append(replySql, inputedId));

		if (replyMaps.isEmpty()) {
			return null;
		}
		for (Map<String, Object> replyMap : replyMaps) {
			ArticleReply reply = new ArticleReply();
			reply.regDate = (String) replyMap.get("regDate");
			reply.updateDate = (String) replyMap.get("updateDate");
			reply.reply = (String) replyMap.get("reply");
			reply.extra__name = (String) replyMap.get("name");
			reply.replyId = (int) replyMap.get("replyId");
			reply.articleId = (int) replyMap.get("articleId");
			articleReplys.add(reply);
		}
		Collections.reverse(replyMaps);
		return articleReplys;
	}

	public void recommand(int memberId, int id) {

	}

	// (게시판 생성) 게시판 이름 중복 체크
	public boolean duplicatedName(String name) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM board WHERE `name` = ?", name);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return false;
		} else {
			return false;
		}
	}

	// (게시판 생성) 게시판 코드 중복 체크
	public boolean duplicatedCode(String code) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM board WHERE `code` = ?", code);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return false;
		} else {
			return false;
		}
	}

	// (게시판 선택) 게시판 목록 나타내기 + 게시물수
	public List<Board> getForPrintBoards() {
		List<Board> boards = new ArrayList<>();
		SecSql sql = new SecSql();
		sql.append("SELECT board.*,COUNT(article.id) AS cnt");
		sql.append(" FROM board INNER JOIN article");
		sql.append(" ON board.id=article.boardId GROUP BY boardId");

		List<Map<String, Object>> boardMaps = MysqlUtil.selectRows(sql);
		if (boardMaps.isEmpty()) {
			return null;
		}
		for (Map<String, Object> boardMap : boardMaps) {
			int id = (int) boardMap.get("id");
			String regDate = (String) boardMap.get("regDate");
			String updateDate = (String) boardMap.get("updateDate");
			String name = (String) boardMap.get("name");
			String code = (String) boardMap.get("code");
			int cnt = (int) boardMap.get("cnt");
			Board board = new Board(id, regDate, updateDate, name, code, cnt);
			boards.add(board);
		}
		return boards;
	}

	// (게시판 선택) code 로 board 불러오기
	public Board setBoardByCode(String inputCode) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM board WHERE `code` = ?", inputCode);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);
		if (boardMap.isEmpty()) {
			return null;
		}

		int id = (int) boardMap.get("id");
		String regDate = (String) boardMap.get("regDate");
		String updateDate = (String) boardMap.get("updateDate");
		String name = (String) boardMap.get("name");
		String code = (String) boardMap.get("code");
		Board board = new Board(id, regDate, updateDate, name, code, 0);

		return board;
	}

	// (게시물 작성) 현재 선택된 넘버로 board 불러오기
	public Board getBoardById(int num) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM board WHERE `id` = ?", num);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);
		if (boardMap.isEmpty()) {
			return null;
		}

		int id = (int) boardMap.get("id");
		String regDate = (String) boardMap.get("regDate");
		String updateDate = (String) boardMap.get("updateDate");
		String name = (String) boardMap.get("name");
		String code = (String) boardMap.get("code");
		Board board = new Board(id, regDate, updateDate, name, code, 0);

		return board;
	}

	public List<Board> getBoards() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Article> getForPrintArticles(int boardId) {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT A.*");
		sql.append(", M.name AS extra__name");
		sql.append(", B.name AS extra__boardName");
		sql.append(", B.code AS extra__boardCode");
		sql.append(" FROM article AS A");
		sql.append(" INNER JOIN `member` AS M");
		sql.append(" ON A.memberId = M.id");
		sql.append(" INNER JOIN `board` AS B");
		sql.append(" ON A.boardId = B.id");
		if (boardId != 0) {
			sql.append(" WHERE A.boardId = ?", boardId);
		}
		sql.append(" ORDER BY A.id DESC");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}

		return articles;
	}

}
