package com.sbs.example.practice.controller;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dto.Board;
import com.sbs.example.practice.service.ArticleService;
import com.sbs.example.practice.service.BuildService;

public class BuildController extends Controller{
	private BuildService buildService;


	public BuildController() {
		buildService=Container.buildService;
	}
	
	public void run(String cmd) {
		if(cmd.equals("build site")) {
			doHtml();
		} 
	}

	private void doHtml() {
		System.out.println("== Html 생성을 시작합니다 ==");
		buildService.buildSite();
	}
}
