package com.artineer.artineersemina232.controller;


import com.artineer.artineersemina232.auth.CurrentUser;
import com.artineer.artineersemina232.dto.StudyDto;
import com.artineer.artineersemina232.entity.Study;
import com.artineer.artineersemina232.entity.UserEntity;
import com.artineer.artineersemina232.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class StudyController {

    private final StudyRepository studyRepository;

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
                .build();


        Study newStudy = studyRepository.save(study);

        newStudy.addManager(userEntity);

//        return "redirect:/study/" + URLEncoder.encode(study.getPath(), StandardCharsets.UTF_8);
        return "redirect:/study/" + uuid;

    }

    @Transactional
    @GetMapping("/study/{uuid}")
    public String showStudy(@PathVariable String uuid, Model model) {
        Optional<Study> study = studyRepository.findByPath(uuid);

        if(study.isEmpty()){
            throw new IllegalArgumentException();
        }

        model.addAttribute("study", study.get());


        return "/study/showStudy";

    }
}
