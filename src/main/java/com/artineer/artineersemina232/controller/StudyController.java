package com.artineer.artineersemina232.controller;


import com.artineer.artineersemina232.auth.CurrentUser;
import com.artineer.artineersemina232.dto.StudyDto;
import com.artineer.artineersemina232.entity.Study;
import com.artineer.artineersemina232.entity.Account;
import com.artineer.artineersemina232.repository.StudyRepository;
import com.artineer.artineersemina232.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
@Slf4j

public class StudyController {

    private final StudyRepository studyRepository;

    private final AccountRepository accountRepository;

    @GetMapping("/study")
    public String showStudyPage(Model model) {
        List<Study> studyList = studyRepository.findAll();

        model.addAttribute("studyList", studyList);

        return "/study/studyPage";
    }


    @GetMapping("/study/new")
    public String createStudyPage(Model model, @CurrentUser Account account) {

        model.addAttribute(account);
        model.addAttribute(new StudyDto());

        return "/study/createStudy";
    }

    @Transactional
    @PostMapping("/study/new")
    public String createNewStudy(@Validated StudyDto studyDto, BindingResult result, @CurrentUser Account account) {

        if (result.hasErrors()) {
            return "/study/createStudy";
        }

        UUID uuid = UUID.randomUUID();

        Study study = Study.builder()
                .title(studyDto.getTitle())
                .shortDescription(studyDto.getShortDescription())
                .studyContent(studyDto.getContent())
                .localDateTime(LocalDateTime.now())
                .author(account.getUsername())
                .path(uuid.toString())
                .published(false)
                .build();


        Study newStudy = studyRepository.save(study);

        newStudy.addManager(account);
        newStudy.addMember(account);


//        return "redirect:/study/" + URLEncoder.encode(study.getPath(), StandardCharsets.UTF_8);
        return "redirect:/study/" + uuid;

    }


    @Transactional
    @GetMapping("/study/{path}")
    public String showStudy(@PathVariable String path, Model model, @CurrentUser Account account) {

        Optional<Study> study = studyRepository.findByPath(path);

        if (study.isEmpty()) {
            throw new IllegalArgumentException("해당하는 스터디가 존재하지않습니다");
        }

        model.addAttribute("study", study.get());
        model.addAttribute("isMember", study.get().getMembers().contains(account));
        model.addAttribute("isManager", study.get().getManagers().contains(account));

        return "/study/showStudy";
    }


    @Transactional
    @GetMapping("/study/{path}/members")
    public String showStudyMembers(@PathVariable String path, Model model, @CurrentUser Account account) {
        Optional<Study> study = studyRepository.findByPath(path);

        if (study.isEmpty()) {
            throw new IllegalArgumentException();
        }

        model.addAttribute("study", study.get());
        model.addAttribute("isMember", study.get().getMembers().contains(account));
        model.addAttribute("isManager", study.get().getManagers().contains(account));

        return "/study/studyMembers";

    }


    @Transactional
    @GetMapping("/study/{path}/addMember")
    public String studyAddMember(@PathVariable String path, Model model, @CurrentUser Account account) {
        Optional<Study> findStudy = studyRepository.findByPath(path);

        if (findStudy.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Study study = findStudy.get();

        if (study.getMembers().contains(accountRepository.findByUsername(account.getUsername()))) {
            throw new AccessDeniedException("이미 스터디에 가입된 사용자입니다");
        }

        study.addMember(account);

        studyRepository.save(study);

        return "redirect:/study/" + path;
    }

    @Transactional
    @GetMapping("/study/{path}/published")
    public String studyOpen(@PathVariable String path, Model model, @CurrentUser Account account) {
        Optional<Study> findStudy = studyRepository.findByPath(path);

        if (findStudy.isEmpty()) {
            throw new IllegalArgumentException();
        }


        Study study = findStudy.get();

        if (!study.getManagers().contains(accountRepository.findByUsername(account.getUsername()))) {
            throw new AccessDeniedException("권한이 없는 사용자입니다");
        }

        if (!findStudy.get().isPublished()) {
            study.open();
            studyRepository.save(study);
        } else {
            study.close();
            studyRepository.save(study);
        }


        return "redirect:/study/" + path;
    }
}
