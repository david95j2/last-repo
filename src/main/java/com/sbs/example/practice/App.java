package com.sbs.example.practice;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.controller.ArticleController;
import com.sbs.example.practice.controller.BuildController;
import com.sbs.example.practice.controller.Controller;
import com.sbs.example.practice.controller.MemberController;

public class App {
	private MemberController memberController;
	private ArticleController articleController;
	private BuildController buildController;

	public App() {
		memberController = Container.memberController;
		articleController = Container.articleController;
		buildController= Container.buildController;
		getTestData();
	}

	private void getTestData() {
		
	}

	public void run() {
		while (true) {
			System.out.printf("명령어 : ");
			String cmd = Container.sc.nextLine();
			if (cmd.equals("system exit")) {
				System.out.println("프로그램 종료");
				break;
			}
			Controller controller = whatKindOfController(cmd);
			try {
				controller.run(cmd);
			} catch (NullPointerException e) {
				System.out.println("알 수 없는 명령어");
				continue;
			}
		}
	}

	private Controller whatKindOfController(String cmd) {
		if (cmd.startsWith("member ")) {
			return memberController;
		} else if (cmd.startsWith("article ")) {
			return articleController;
		} else if (cmd.startsWith("build")) {
			return buildController;
		}
		return null;
	}
}
