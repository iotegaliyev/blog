package com.example.project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "header_eng", nullable = false)
    private String headerEng;
    @Column(name = "header_ru", nullable = false)
    private String headerRu;
    @Column(name = "header_kk", nullable = false)
    private String headerKk;

    @Column(name = "text_eng", nullable = false, columnDefinition = "TEXT")
    private String textEng;
    @Column(name = "text_ru", nullable = false, columnDefinition = "TEXT")
    private String textRu;
    @Column(name = "text_kk", nullable = false, columnDefinition = "TEXT")
    private String textKk;

    @Column(name = "author_eng", nullable = false)
    private String authorEng;
    @Column(name = "author_ru", nullable = false)
    private String authorRu;
    @Column(name = "author_kk", nullable = false)
    private String authorKk;

    @Column(name = "createdAt", nullable = false)
    private String createdAt;
    @Column(name = "modifiedAt", nullable = true)
    private String modifiedAt;

}