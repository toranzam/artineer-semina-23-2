package com.artineer.artineersemina232.controller;


import com.artineer.artineersemina232.dto.ArticleDto;
import com.artineer.artineersemina232.dto.UserDto;
import com.artineer.artineersemina232.entity.Article;
import com.artineer.artineersemina232.repository.ArticleRepository;
import com.artineer.artineersemina232.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final UserService userService;

    private final ArticleRepository articleRepository; //시작한닼ㅋ

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

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

        return "/show";
    }


    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("userDto", new UserDto());
//        System.out.println("UserController.signup.Get");
        return "signUpForm";
    }

    @PostMapping("/signUp") //@Valid = 검증, 회원가입 중 오류가 나면 리턴한다. 에러가 없으면 유저 서비스에서 새로운 유저를 만드는 프로세서를 거친다.
    public String signUp(UserDto userDto) {
//        if (errors.hasErrors()) {
//            System.out.println("작동안됨");
//            return "form/signupForm";
//        }
//        System.out.println("UserController.signUp.Post");

        userService.saveNewUser(userDto);

        return "redirect:/index"; //요청된 장소로 가야함 Form/ 쓰면 안됨. 써서 없는 페이지로 가고 있었음.
    }

//    @GetMapping("/")
//    public String indexPage() {
//        return "form/index";
//    }


}
