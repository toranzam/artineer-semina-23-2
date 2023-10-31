package com.artineer.artineersemina232.controller;


import com.artineer.artineersemina232.dto.ArticleDto;
import com.artineer.artineersemina232.dto.UserDto;
import com.artineer.artineersemina232.entity.Article;
import com.artineer.artineersemina232.repository.ArticleRepository;
import com.artineer.artineersemina232.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final UserService userService;

    private final ArticleRepository articleRepository; //시작한닼ㅋ

    @GetMapping("/articles")
    public String showArticles(Model model) {

        List<Article> articles = articleRepository.findAll();

        model.addAttribute("articles", articles);

        return "articlesTest";
    }

    @GetMapping("/articles/new")
    public String showCreateNewArticle() {


        return "createArticle";
    }

    @PostMapping("/articles/new")
    public String createNewArticle(@ModelAttribute ArticleDto articleDto) {

        Article article = Article.builder()
                .title(articleDto.getTitle())
                .author(articleDto.getAuthor())
                .content(articleDto.getContent())
                .localDateTime(LocalDateTime.now())
                .build();

        System.out.println("controller.createNewArticle");
        System.out.println("articleDto = " + articleDto);

        articleRepository.save(article);

        return "redirect:/articles";

    }

    @GetMapping("/articles/{id}")
    public String showDetail(@PathVariable Long id, Model model){
        Optional<Article> article = articleRepository.findById(id);

        model.addAttribute("article", article.get());

        return "showArticle";
    }


}
