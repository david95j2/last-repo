package com.sbs.example.practice.container;

import java.util.Scanner;

import com.sbs.example.practice.controller.ArticleController;
import com.sbs.example.practice.controller.BuildController;
import com.sbs.example.practice.controller.MemberController;
import com.sbs.example.practice.dao.ArticleDao;
import com.sbs.example.practice.dao.MemberDao;
import com.sbs.example.practice.service.ArticleService;
import com.sbs.example.practice.service.BuildService;
import com.sbs.example.practice.service.MemberService;
import com.sbs.example.practice.session.Session;

public class Container {

	public static Scanner sc;
	public static Session session;

	public static MemberDao memberDao;
	public static ArticleDao articleDao;

	public static MemberService memberService;
	public static ArticleService articleService;
	public static BuildService buildService;
	
	public static MemberController memberController;
	public static ArticleController articleController;
	public static BuildController buildController;

	static {
		sc = new Scanner(System.in);
		session = new Session();

		memberDao = new MemberDao();
		articleDao = new ArticleDao();

		memberService = new MemberService();
		articleService = new ArticleService();
		buildService = new BuildService();

		memberController = new MemberController();
		articleController = new ArticleController();
		buildController = new BuildController();
	}
}
