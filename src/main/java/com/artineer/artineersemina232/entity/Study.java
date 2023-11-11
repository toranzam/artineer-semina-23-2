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

    @ManyToMany
    private Set<UserEntity> members = new HashSet<>();

    private String author;

    private String title;

    private String shortDescription;

    @Lob
    private String studyContent;

    @Column(unique = true)
    private String path;

    private LocalDateTime localDateTime;

    private boolean published;

    public void addManager(UserEntity userEntity) {
        this.managers.add(userEntity);
    }

    public void addMember(UserEntity userEntity) {
        this.members.add(userEntity);
    }

    @Builder
    public Study(Long id, String author, String title, String shortDescription, String studyContent, String path, LocalDateTime localDateTime, boolean published) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.shortDescription = shortDescription;
        this.studyContent = studyContent;
        this.path = path;
        this.localDateTime = localDateTime;
        this.published = published;
    }


}
