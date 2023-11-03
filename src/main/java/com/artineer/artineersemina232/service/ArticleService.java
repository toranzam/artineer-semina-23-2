package com.artineer.artineersemina232.service;

import com.artineer.artineersemina232.dto.ArticleDto;
import com.artineer.artineersemina232.entity.Article;
import com.artineer.artineersemina232.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleService {

    public Article toEntity(ArticleDto articleDto, UserEntity userEntity) {
        return Article.builder()
                .title(articleDto.getTitle())
                .author(userEntity.getUsername())
                .content(articleDto.getContent())
                .localDateTime(LocalDateTime.now())
                .build();
    }
}
