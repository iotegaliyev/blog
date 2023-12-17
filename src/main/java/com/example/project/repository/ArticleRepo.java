package com.example.project.repository;

import com.example.project.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article, Long> {
    List<Article> findByHeaderContaining(String keyword);
}
