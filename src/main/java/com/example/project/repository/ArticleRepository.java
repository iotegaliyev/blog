package com.example.project.repository;

import com.example.project.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByHeaderEngContaining(String keyword);
}
