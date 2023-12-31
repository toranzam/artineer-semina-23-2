package com.artineer.artineersemina232.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Set<Account> managers = new HashSet<>();

    @ManyToMany
    private Set<Account> members = new HashSet<>();

    private String author;

    private String title;

    private String shortDescription;

    @Lob
    private String studyContent;

    @Column(unique = true)
    private String path;

    private LocalDateTime localDateTime;

    private boolean published;

    public void addManager(Account userEntity) {
        this.managers.add(userEntity);
    }

    public void addMember(Account userEntity) {
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

    public void open() {
        this.published = true;
    }

    public void close() {
        this.published = false;
    }
}
