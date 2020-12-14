package com.sbs.example.practice.controller;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dto.Board;
import com.sbs.example.practice.service.ArticleService;
import com.sbs.example.practice.service.BuildService;

public class BuildController extends Controller{
	private BuildService buildService;
	private ArticleService articleService;

	public BuildController() {
		buildService=Container.buildService;
		articleService=Container.articleService;
	}
	
	public void run(String cmd) {
		if(cmd.equals("build site")) {
			doHtml();
		} else if (cmd.equals("build article")) {
			doMakeList();
		} 
	}

	private void doMakeList() {
		int id = Container.session.getBoardId();
		Board board = articleService.getBoardById(id);
		System.out.printf("== %s 게시판 리스트를 생성합니다 ==\n", board.name);
		buildService.buildList(board.code,board.id);
	}


	private void doHtml() {
		System.out.println("== Html 생성을 시작합니다 ==");
		buildService.buildSite();
	}
}
