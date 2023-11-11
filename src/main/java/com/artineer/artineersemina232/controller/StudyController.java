package com.artineer.artineersemina232.controller;


import com.artineer.artineersemina232.auth.CurrentUser;
import com.artineer.artineersemina232.dto.StudyDto;
import com.artineer.artineersemina232.entity.Study;
import com.artineer.artineersemina232.entity.UserEntity;
import com.artineer.artineersemina232.repository.StudyRepository;
import com.artineer.artineersemina232.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Set;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
@Slf4j

public class StudyController {

    private final StudyRepository studyRepository;

    private final UserRepository userRepository;

    @GetMapping("/study")
    public String showStudyPage(Model model) {
        List<Study> studyList = studyRepository.findAll();

        model.addAttribute("studyList", studyList);

        return "/study/studyPage";
    }


    @GetMapping("/study/new")
    public String createStudyPage(Model model, @CurrentUser UserEntity userEntity) {

        model.addAttribute(userEntity);
        model.addAttribute(new StudyDto());

        return "/study/createStudy";
    }

    @Transactional
    @PostMapping("/study/new")
    public String createNewStudy(@Validated StudyDto studyDto, BindingResult result, @CurrentUser UserEntity userEntity) {

        if(result.hasErrors()){
            return "/study/createStudy";
        }

        UUID uuid = UUID.randomUUID();

        Study study = Study.builder()
                .title(studyDto.getTitle())
                .shortDescription(studyDto.getShortDescription())
                .studyContent(studyDto.getContent())
                .localDateTime(LocalDateTime.now())
                .author(userEntity.getUsername())
                .path(uuid.toString())
                .published(false)
                .build();


        Study newStudy = studyRepository.save(study);

        newStudy.addManager(userEntity);
        newStudy.addMember(userEntity);


//        return "redirect:/study/" + URLEncoder.encode(study.getPath(), StandardCharsets.UTF_8);
        return "redirect:/study/" + uuid;

    }


    @Transactional
    @GetMapping("/study/{path}")
    public String showStudy(@PathVariable String path, Model model, @CurrentUser UserEntity userEntity) {
        Optional<Study> study = studyRepository.findByPath(path);

        if(study.isEmpty()){
            throw new IllegalArgumentException();
        }

        model.addAttribute(userEntity);
        model.addAttribute("study", study.get());


        return "/study/showStudy";

    }

    @Transactional
    @GetMapping("/study/{path}/members")
    public String showStudyMembers(@PathVariable String path, Model model, @CurrentUser UserEntity userEntity) {
        Optional<Study> study = studyRepository.findByPath(path);

        if(study.isEmpty()){
            throw new IllegalArgumentException();
        }


        model.addAttribute(userEntity);
        model.addAttribute("study", study.get());

        return "/study/studyMembers";

    }

    @Transactional
    @GetMapping("/study/{path}/settings")
    public String showStudySetting(@PathVariable String path, Model model, @CurrentUser UserEntity userEntity) {
        Optional<Study> study = studyRepository.findByPath(path);

        if(study.isEmpty()){
            throw new IllegalArgumentException();
        }

        model.addAttribute(userEntity);
        model.addAttribute("study", study.get());

        return "/study/studyMembers";

    }

    @Transactional
    @GetMapping("/study/{path}/addMember")
    public String studyAddMember(@PathVariable String path, Model model, @CurrentUser UserEntity userEntity) {
        Optional<Study> findStudy = studyRepository.findByPath(path);

        if(findStudy.isEmpty()){
            throw new IllegalArgumentException();
        }

        Study study = findStudy.get();

        if (study.getMembers().contains(userRepository.findByUsername(userEntity.getUsername()))){
            throw new IllegalArgumentException("이미 스터디에 가입된 사용자입니다");
        }

        study.addMember(userEntity);

        studyRepository.save(study);

        return "redirect:/study";
    }
}
