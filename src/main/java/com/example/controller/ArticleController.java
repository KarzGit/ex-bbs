package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.service.ArticleService;
import com.example.service.CommentService;

@Controller
@RequestMapping("/bbs")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private CommentService commentService;

	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleService.findAll();

		for (Article article : articleList) {
			article.setCommentList(commentService.findByArticleId(article.getId()));
		}
		model.addAttribute("articleList", articleList);

		return "bbs";
	}

	@ModelAttribute
	private ArticleForm articleFormsetUpForm() {
		return new ArticleForm();
	}

	@RequestMapping("/insertArticle")
	public String insertArticle(ArticleForm form, Model model) {
		Article article = new Article();
		article.setName(form.getName());
		article.setContent(form.getContent());
		articleService.insert(article);
		return index(model);

	}

	@ModelAttribute
	private CommentForm commentFormsetUpForm() {
		return new CommentForm();
	}

	@RequestMapping("/insertComment")
	public String insertComment(CommentForm form, Model model) {
		return index(model);
	}

}
