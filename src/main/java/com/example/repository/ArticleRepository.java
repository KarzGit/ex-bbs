package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

@Repository
public class ArticleRepository {
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	public List<Article> findAll() {
		String sql = "select * from articles";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		return articleList;
	}

	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "insert into articles(name, content) values(:name,:content)";
		template.update(sql, param);
	}

	public void deleteById(int id) {
		String sql = "delete from articles where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
