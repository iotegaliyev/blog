package com.example.project.model;

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
    @Column(name = "header", nullable = false)
    private String header;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "createdAt", nullable = false)
    private String createdAt;
    @Column(name = "modifiedAt", nullable = true)
    private String modifiedAt;

}