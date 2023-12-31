package com.artineer.artineersemina232.service;

import com.artineer.artineersemina232.dto.ArticleDto;
import com.artineer.artineersemina232.entity.Article;
import com.artineer.artineersemina232.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ArticleService {

    public Article toEntity(ArticleDto articleDto, Account userEntity) {
        return Article.builder()
                .title(articleDto.getTitle())
                .author(userEntity.getUsername())
                .content(articleDto.getContent())
                .localDateTime(LocalDateTime.now())
                .build();
    }
}
