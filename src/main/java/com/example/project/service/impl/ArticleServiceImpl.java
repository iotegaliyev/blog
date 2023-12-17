package com.example.project.service.impl;

import com.example.project.DTO.ArticleDTO;
import com.example.project.service.ArticleService;
import com.example.project.DTO.ArticleResponse;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.Article;
import com.example.project.repository.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    @Override
    public ArticleResponse getAllArticles(int pageNo, int pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Article> posts = articleRepo.findAll(pageable);
        List<Article> listOfPosts = posts.getContent();

        List<ArticleDTO> content = listOfPosts.stream().map(article -> mapToDTO(article)).collect(Collectors.toList());
        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setContent(content);
        articleResponse.setPageNo(posts.getTotalPages());
        articleResponse.setPageSize(posts.getTotalPages());
        articleResponse.setTotalElements(articleResponse.getTotalElements());
        articleResponse.setTotalPages(posts.getTotalPages());
        articleResponse.setLast(posts.isLast());

        return articleResponse;
    }

    public List<Article> searchArticlesByHeader(String keyword) {
        return articleRepo.findByHeaderContaining(keyword);
    }


    @Override
    public ArticleDTO getById(long id) {
        Article a = articleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article", "id", id));
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(a.getId());
        articleDTO.setHeader(a.getHeader());
        articleDTO.setText(a.getText());
        articleDTO.setAuthor(a.getAuthor());

        return articleDTO;
    }

    @Override
    public ArticleDTO createArticle(ArticleDTO a) {
        Article article = new Article();
        article.setHeader(a.getHeader());
        article.setText(a.getText());
        article.setAuthor(a.getAuthor());
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        article.setCreatedAt(timestamp);
        articleRepo.save(article);

        a.setId(article.getId());
        return a;
    }

    @Override
    public void deleteById(long id) {
        articleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article", "id", id));
        articleRepo.deleteById(id);
    }


    @Override
    public void updateArticle(long id, ArticleDTO articleDTO) {
        articleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article", "id", id));
        Article a = articleRepo.getById(id);
        a.setHeader(articleDTO.getHeader());
        a.setText(articleDTO.getText());
        a.setAuthor(articleDTO.getAuthor());
        articleRepo.save(a);
    }


    private ArticleDTO mapToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setHeader(article.getHeader());
        articleDTO.setText(article.getText());
        articleDTO.setAuthor(article.getAuthor());
        return articleDTO;
    }


    private Article mapToEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setHeader(articleDTO.getHeader());
        article.setText(articleDTO.getText());
        article.setAuthor(articleDTO.getAuthor());
        return article;
    }
}
