package com.sbs.example.practice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.practice.Container;
import com.sbs.example.practice.apidto.DisqusApiDataListThread;
import com.sbs.example.practice.dto.Article;
import com.sbs.example.practice.util.Util;

public class DisqusApiService {
	public Map<String, Object> getArticleData(Article article) {
		String fileName = Container.buildService.getArticleDetailFileName(article.getId());
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		DisqusApiDataListThread disqusApiDataListThread = (DisqusApiDataListThread) Util.callApiResponseTo(
				DisqusApiDataListThread.class, url, "api_key=" + Container.config.getDisqusApiKey(),
				"forum=" + Container.config.getDisqusForumName(), "thread:ident=" + fileName);

		if (disqusApiDataListThread == null) {
			return null;
		}

		Map<String, Object> rs = new HashMap<>();
		rs.put("likesCount", disqusApiDataListThread.response.get(0).likes);
		rs.put("commentsCount", disqusApiDataListThread.response.get(0).posts);

		return rs;
	}

	public void updateArticleCounts() {
		List<Article> articles = Container.articleService.getForPrintArticles();

		for (Article article : articles) {
			Map<String, Object> disqusArticleData = Container.disqusApiService.getArticleData(article);

			if (disqusArticleData != null) {
				int likesCount = (int) disqusArticleData.get("likesCount");
				int commentsCount = (int) disqusArticleData.get("commentsCount");

				Map<String, Object> modifyArgs = new HashMap<>();
				modifyArgs.put("id", article.getId());
				modifyArgs.put("likesCount", likesCount);
				modifyArgs.put("commentsCount", commentsCount);

				Container.articleService.modify(modifyArgs);
			}
		}
	}

}
