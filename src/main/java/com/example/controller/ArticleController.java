package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.service.ArticleService;

@Controller
@RequestMapping("/bbs")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleService.findAll();
		model.addAttribute("articleList", articleList);

		return "bbs";
	}

	@ModelAttribute
	private ArticleForm setUpForm() {
		return new ArticleForm();
	}

	@RequestMapping("/insert")
	public String insertArticle(ArticleForm form, Model model) {
		Article article = new Article();
		article.setName(form.getName());
		article.setContent(form.getContent());
		articleService.insert(article);
		return index(model);

	}

}
