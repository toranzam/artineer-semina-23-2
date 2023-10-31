package com.artineer.artineersemina232.repository;


import com.artineer.artineersemina232.entity.Article;
import com.artineer.artineersemina232.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username); //메소드 오버라이딩 함.

}
