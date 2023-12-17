package com.example.project.service;

import com.example.project.DTO.ArticleDTO;
import com.example.project.DTO.ArticleResponse;
import com.example.project.model.Article;

import java.util.List;

public interface ArticleService {
    ArticleResponse getAllArticles(int pageNo, int pageSize, String sortBy, String sortOrder);

    List<Article> searchArticlesByHeader(String keyword);

    ArticleDTO getById(long id);

    ArticleDTO createArticle(ArticleDTO a);

    void deleteById(long id);

    void updateArticle(long id, ArticleDTO articleDTO);
}