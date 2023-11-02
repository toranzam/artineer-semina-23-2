package com.artineer.artineersemina232.auth;

import com.artineer.artineersemina232.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class AuthUser extends User {
    
    private final UserEntity userEntity;

    public AuthUser(UserEntity userEntity) {
        super(userEntity.getUsername(), userEntity.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.userEntity = userEntity; //필드 변수에 넣어줌.
    }
}
