package com.artineer.artineersemina232.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyDto {

    private String title;

    private String shortDescription;

    private String content;

}
