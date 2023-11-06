package com.artineer.artineersemina232.repository;

import com.artineer.artineersemina232.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {


    Optional<Study> findByPath(String uuid);
}
