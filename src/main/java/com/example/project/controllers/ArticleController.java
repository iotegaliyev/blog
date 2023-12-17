package com.example.project.controllers;

import com.example.project.DTO.ArticleDTO;
import com.example.project.DTO.ArticleResponse;
import com.example.project.models.Article;
import com.example.project.security.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable("id") int articleId,
                                                 @RequestParam String locale) {
        return ResponseEntity.ok(articleService.getById(articleId, locale));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article);
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ArticleResponse> getAllArticles(@RequestParam int pageNo,
                                                          @RequestParam int pageSize,
                                                          @RequestParam String sortBy,
                                                          @RequestParam String sortOrder,
                                                          @RequestParam String locale) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getAllArticles(pageNo, pageSize, sortBy, sortOrder, locale));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable("id") Long articleId) {
        articleService.deleteById(articleId);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void updateArticle(@PathVariable("id") Long articleId, @RequestBody Article article) {
        articleService.updateArticle(articleId, article);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Article>> searchProducts(@RequestParam String keyword) {
        List<Article> articles = articleService.searchArticlesByHeader(keyword);
        return ResponseEntity.ok(articles);
    }
}