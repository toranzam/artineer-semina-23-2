package com.artineer.artineersemina232.controller;


import com.artineer.artineersemina232.auth.CurrentUser;
import com.artineer.artineersemina232.dto.ArticleDto;
import com.artineer.artineersemina232.entity.Article;
import com.artineer.artineersemina232.entity.Account;
import com.artineer.artineersemina232.repository.ArticleRepository;
import com.artineer.artineersemina232.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    private final ArticleRepository articleRepository;

    static final String SHOW_ARTICLES_PAGE = "/articles";

    static final String NEW_ARTICLE_FORM = "/articles/new";

    @GetMapping(SHOW_ARTICLES_PAGE)
    public String showArticles(Model model) {

        List<Article> articles = articleRepository.findAll();

        model.addAttribute("articles", articles);

        return "/article/articles";
    }

    @GetMapping(NEW_ARTICLE_FORM)
    public String showCreateNewArticle() {

        return "/article/createArticle";
    }

    @PostMapping(NEW_ARTICLE_FORM)
    public String createNewArticle(@ModelAttribute ArticleDto articleDto, @CurrentUser Account account) {



        Article article = articleService.toEntity(articleDto, account);

        articleRepository.save(article);

        return "redirect:/articles";
    }


    @GetMapping("/articles/{id}")
    public String showDetail(@PathVariable Long id, @CurrentUser Account account, Model model) {
        Optional<Article> article = articleRepository.findById(id);

        if (article.isEmpty()) {
            throw new IllegalArgumentException("조회할 게시글이 존재하지 않습니다.");
        }

        model.addAttribute("article", article.get());
        model.addAttribute(account);

        return "/article/showArticle";
    }

    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable Long id, @CurrentUser Account account) {

        Optional<Article> findArticle = articleRepository.findById(id);

        if (findArticle.isEmpty()) {
            throw new IllegalArgumentException("삭제할 게시물이 존재하지않습니다");
        }

        if (!findArticle.get().getAuthor().equals(account.getUsername())){
            throw new RuntimeException("삭제할 권한이 없습니다");
        }

        articleRepository.delete(findArticle.get());

        return "redirect:/articles";
    }

    @GetMapping("articles/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, @CurrentUser Account account) {
        Optional<Article> findArticle = articleRepository.findById(id);

        if(findArticle.isEmpty()){
            throw new IllegalArgumentException("수정할 게시물이 존재하지 않습니다");
        }

        if (!findArticle.get().getAuthor().equals(account.getUsername())){
            throw new IllegalArgumentException("수정할 권한이 없습니다");
        }

        model.addAttribute("article", findArticle.get());

        return "/article/editArticle";
    }


}
