package com.artineer.artineersemina232.controller;

import com.artineer.artineersemina232.auth.CurrentUser;
import com.artineer.artineersemina232.dto.AccountDto;
import com.artineer.artineersemina232.dto.ArticleDto;
import com.artineer.artineersemina232.dto.StudyDto;
import com.artineer.artineersemina232.entity.Account;
import com.artineer.artineersemina232.entity.Study;
import com.artineer.artineersemina232.repository.AccountRepository;
import com.artineer.artineersemina232.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/study/{path}/settings")
@Slf4j
public class StudySettingsController {

    private final StudyRepository studyRepository;

    private final AccountRepository accountRepository;

    @Transactional
    @GetMapping("")
    public String showStudySetting(@PathVariable String path, Model model, @CurrentUser Account account) {
        Optional<Study> byPath = studyRepository.findByPath(path);

        if (byPath.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Study study = byPath.get();

        if(!study.getManagers().contains(account) && !study.getMembers().contains(account)){
            throw new AccessDeniedException("설정 접근권한이 없는 사용자입니다");
        }

        model.addAttribute("study", study);
        model.addAttribute("isMember", byPath.get().getMembers().contains(account));
        model.addAttribute("isManager", byPath.get().getManagers().contains(account));

        return "/study/studySettings";
    }

    @Transactional
    @GetMapping("/description")
    public String showStudySettingDescription(@PathVariable String path, Model model, @CurrentUser Account account) {
        Optional<Study> byPath = studyRepository.findByPath(path);

        if (byPath.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Study study = byPath.get();
        
        
        StudyDto studyDto = StudyDto.builder()
                .title(study.getTitle())
                .content(study.getStudyContent())
                .shortDescription(study.getShortDescription())
                .build();


        model.addAttribute("study", byPath.get());
        model.addAttribute("isMember", byPath.get().getMembers().contains(account));
        model.addAttribute("isManager", byPath.get().getManagers().contains(account));
        model.addAttribute("studyDto", studyDto);


        return "/study/settings/description";
    }

    @Transactional
    @PostMapping("/update")
    public String updateStudy(@PathVariable String path, StudyDto studyDto, @CurrentUser Account account) {
        Optional<Study> byPath = studyRepository.findByPath(path);

        if (byPath.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Study study = byPath.get();

        if(!study.getManagers().contains(account)){
            throw new AccessDeniedException("수정할 권한이 없는 유저입니다");
        }

        study.setTitle(studyDto.getTitle());
        study.setStudyContent(studyDto.getContent());
        study.setShortDescription(studyDto.getShortDescription());


        return "redirect:/study/" + study.getPath() + "/settings";

    }

}
