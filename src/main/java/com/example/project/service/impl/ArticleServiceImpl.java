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
    public ArticleResponse getAllArticles(int pageNo, int pageSize, String sortBy, String sortOrder, String locale) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Article> posts = articleRepo.findAll(pageable);
        List<Article> listOfPosts = posts.getContent();

        List<ArticleDTO> content = listOfPosts.stream().map(article -> mapToDTO(article, locale)).collect(Collectors.toList());
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
        return articleRepo.findByHeaderEngContaining(keyword);
    }


    @Override
    public ArticleDTO getById(long id, String locale) {
        Article a = articleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article", "id", id));
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(a.getId());
        if (locale.equals("ru")) {
            articleDTO.setHeader(a.getHeaderRu());
            articleDTO.setText(a.getTextRu());
            articleDTO.setAuthor(a.getAuthorRu());
        } else if (locale.equals("kk")) {
            articleDTO.setHeader(a.getHeaderKk());
            articleDTO.setText(a.getTextKk());
            articleDTO.setAuthor(a.getAuthorKk());
        } else {
            articleDTO.setHeader(a.getHeaderEng());
            articleDTO.setText(a.getTextEng());
            articleDTO.setAuthor(a.getAuthorEng());
        }

        return articleDTO;
    }

    @Override
    public Article createArticle(Article a) {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        a.setCreatedAt(timestamp);
        articleRepo.save(a);

        a.setId(a.getId());
        return a;
    }

    @Override
    public void deleteById(long id) {
        articleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article", "id", id));
        articleRepo.deleteById(id);
    }


    @Override
    public void updateArticle(long id, Article article) {
        articleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article", "id", id));
        Article a = articleRepo.getById(id);
        a.setHeaderEng(article.getHeaderEng());
        a.setHeaderRu(article.getHeaderRu());
        a.setHeaderKk(article.getHeaderKk());
        a.setTextEng(article.getTextEng());
        a.setTextRu(article.getTextRu());
        a.setTextKk(article.getTextKk());
        a.setAuthorEng(article.getAuthorEng());
        a.setAuthorRu(article.getAuthorRu());
        a.setAuthorKk(article.getAuthorKk());
        articleRepo.save(a);
    }


    private ArticleDTO mapToDTO(Article article, String locale) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        if (locale.equals("ru")) {
            articleDTO.setHeader(article.getHeaderRu());
            articleDTO.setText(article.getTextRu());
            articleDTO.setAuthor(article.getAuthorRu());
        } else if (locale.equals("kk")) {
            articleDTO.setHeader(article.getHeaderKk());
            articleDTO.setText(article.getTextKk());
            articleDTO.setAuthor(article.getAuthorKk());
        } else {
            articleDTO.setHeader(article.getHeaderEng());
            articleDTO.setText(article.getTextEng());
            articleDTO.setAuthor(article.getAuthorEng());
        }

        return articleDTO;
    }

    /* Тебе нужен этот кусок кода? Он же нигде не используется */

//    private Article mapToEntity(ArticleDTO articleDTO) {
//        Article article = new Article();
//        article.setId(articleDTO.getId());
//        article.setHeader(articleDTO.getHeader());
//        article.setText(articleDTO.getText());
//        article.setAuthor(articleDTO.getAuthor());
//        return article;
//    }
}
