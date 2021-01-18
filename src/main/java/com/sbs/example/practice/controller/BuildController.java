package com.sbs.example.practice.controller;

import com.sbs.example.practice.Container;
import com.sbs.example.practice.service.BuildService;

public class BuildController extends Controller {
	private BuildService buildService;

	public BuildController() {
		buildService = Container.buildService;
	}

	public void doCommand(String cmd) {
		if (cmd.startsWith("build site")) {
			doBuildSite(cmd);
		}
	}

	private void doBuildSite(String cmd) {
		System.out.println("== 사이트 생성 ==");
		
		buildService.buildSite();
	}
}
