package com.artineer.artineersemina232.controller;


import com.artineer.artineersemina232.dto.UserDto;
import com.artineer.artineersemina232.repository.ArticleRepository;
import com.artineer.artineersemina232.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final ArticleRepository articleRepository; //시작한닼ㅋ



    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "signUpForm";
    }

    @PostMapping("/signUp") //@Valid = 검증, 회원가입 중 오류가 나면 리턴한다. 에러가 없으면 유저 서비스에서 새로운 유저를 만드는 프로세서를 거친다.
    public String signUp(UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            log.info("회원가입 실패={}", userDto);
            return "signUpForm";
        }
//        System.out.println("UserController.signUp.Post");

        userService.saveNewUser(userDto);

        return "redirect:/loginPage"; //요청된 장소로 가야함 Form/ 쓰면 안됨. 써서 없는 페이지로 가고 있었음.
    }
}
