package com.example.project.controller;

import com.example.project.DTO.ArticleDTO;
import com.example.project.DTO.ArticleResponse;
import com.example.project.model.Article;
import com.example.project.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable("id") int articleId) {
        return ResponseEntity.ok(articleService.getById(articleId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
        ArticleDTO articleDTO = articleService.createArticle(new ArticleDTO(article.getHeader(), article.getText(), article.getAuthor()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleDTO);
    }

    @GetMapping()
    public ResponseEntity<ArticleResponse> getAllArticles(@RequestParam int pageNo,
                                                          @RequestParam int pageSize,
                                                          @RequestParam String sortBy,
                                                          @RequestParam String sortOrder) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getAllArticles(pageNo, pageSize, sortBy, sortOrder));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable("id") Long articleId) {
        articleService.deleteById(articleId);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateArticle(@PathVariable("id") Long articleId, @RequestBody ArticleDTO articleDTO) {
        articleService.updateArticle(articleId, articleDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Article>> searchProducts(@RequestParam String keyword) {
        List<Article> articles = articleService.searchArticlesByHeader(keyword);
        return ResponseEntity.ok(articles);
    }
}