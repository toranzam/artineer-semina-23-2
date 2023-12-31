package com.artineer.artineersemina232.controller;


import com.artineer.artineersemina232.auth.CurrentUser;
import com.artineer.artineersemina232.dto.AccountDto;
import com.artineer.artineersemina232.entity.Account;
import com.artineer.artineersemina232.repository.AccountRepository;
import com.artineer.artineersemina232.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    private final AccountRepository accountRepository;

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("userDto", new AccountDto());
        return "signUpForm";
    }

    @PostMapping("/signUp") //@Valid = 검증, 회원가입 중 오류가 나면 리턴한다. 에러가 없으면 유저 서비스에서 새로운 유저를 만드는 프로세서를 거친다.
    public String signUp(@Validated AccountDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            log.info("회원가입 실패={}", userDto);
            return "signUpForm";
        }

        accountService.saveNewUser(userDto);

        // 자동로그인
//        accountService.login(userDto.getUsername());
        return "redirect:/loginPage"; //요청된 장소로 가야함 Form/ 쓰면 안됨. 써서 없는 페이지로 가고 있었음.
    }

    @GetMapping("/profile/{username}")
    public String showProfile(@PathVariable String username, Model model, @CurrentUser Account account) {

        Account byUsername = accountRepository.findByUsername(username);
        if(byUsername == null){
            throw new IllegalArgumentException(username + "에 해당하는 사용자가 존재하지않습니다");
        }

        model.addAttribute("account", byUsername);
        model.addAttribute("isOwner", account.equals(byUsername));

        return "account/profile";
    }
}
