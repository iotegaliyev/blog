package com.example.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private long id;
    private String header;
    private String text;
    private String author;

    public ArticleDTO(String header, String text, String author) {
        this.header = header;
        this.text = text;
        this.author = author;
    }
}
