package com.artineer.artineersemina232.controller;


import com.artineer.artineersemina232.auth.CurrentUser;
import com.artineer.artineersemina232.dto.StudyDto;
import com.artineer.artineersemina232.entity.Study;
import com.artineer.artineersemina232.entity.UserEntity;
import com.artineer.artineersemina232.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public String createStudyPage(Model model) {

        model.addAttribute(new StudyDto());

        return "/study/createStudy";
    }

    @PostMapping("/study/new")
    public String createNewStudy(StudyDto studyDto, @CurrentUser UserEntity userEntity) {

        Study study = Study.builder()
                .title(studyDto.getStudyName())
                .shortDescription(studyDto.getShortDescription())
                .studyContent(studyDto.getStudyContent())
                .localDateTime(LocalDateTime.now())
                .author(userEntity.getUsername())
                .build();

        studyRepository.save(study);


        return "redirect:/study";
    }

    @GetMapping("/study/{id}")
    public String showStudy(@PathVariable Long id, Model model) {
        Optional<Study> study = studyRepository.findById(id);

        if(study.isEmpty()){
            throw new IllegalArgumentException();
        }

        model.addAttribute("study", study.get());


        return "/study/showStudy";
    }
}
