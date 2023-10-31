package com.artineer.artineersemina232.auth;

import lombok.Getter;

import java.util.List;

@Getter
public class AuthUser extends User{
    private UserEntity userEntity;

    public AuthUser(UserEntity userEntity) {
        super(userEntity.getUsername(), userEntity.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.userEntity = userEntity; //필드 변수에 넣어줌.
    }
}
