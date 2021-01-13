package com.sbs.example.practice.container;

import java.util.Scanner;

import com.sbs.example.practice.AppConfig;
import com.sbs.example.practice.controller.ArticleController;
import com.sbs.example.practice.controller.BuildController;
import com.sbs.example.practice.controller.Controller;
import com.sbs.example.practice.controller.MemberController;
import com.sbs.example.practice.service.ArticleService;
import com.sbs.example.practice.service.BuildService;
import com.sbs.example.practice.service.DisqusApiService;
import com.sbs.example.practice.service.GoogleAnalyticsApiService;
import com.sbs.example.practice.service.MemberService;
import com.sbs.example.practice.session.Session;

public class Container {

	public static Scanner sc;

	public static MemberService memberService;
	public static ArticleService articleService;
	public static BuildService buildService;
	public static DisqusApiService disqusApiService;
	public static GoogleAnalyticsApiService googleAnalyticsApiService;

	public static Controller articleController;
	public static Controller memberController;
	public static Controller buildController;

	public static Session session;

	public static AppConfig config;

	static {
		config = new AppConfig();
		
		sc = new Scanner(System.in);

		session = new Session();

		googleAnalyticsApiService = new GoogleAnalyticsApiService();
		disqusApiService = new DisqusApiService();
		memberService = new MemberService();
		articleService = new ArticleService();
		buildService = new BuildService();
		
		articleController = new ArticleController();
		memberController = new MemberController();
		buildController = new BuildController();

	}

}
