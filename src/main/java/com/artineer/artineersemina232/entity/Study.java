package com.artineer.artineersemina232.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Set<UserEntity> managers = new HashSet<>();


    private String author;

    private String title;

    private String shortDescription;

    @Lob
    private String studyContent;

    @Column(unique = true)
    private String path;

    private LocalDateTime localDateTime;

    public void addManager(UserEntity userEntity) {
        this.managers.add(userEntity);
    }

    @Builder
    public Study(Long id, String author, String title, String shortDescription, String studyContent, String path, LocalDateTime localDateTime) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.shortDescription = shortDescription;
        this.studyContent = studyContent;
        this.path = path;
        this.localDateTime = localDateTime;
    }
}
