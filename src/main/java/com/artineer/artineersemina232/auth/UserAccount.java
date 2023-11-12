package com.artineer.artineersemina232.auth;

import com.artineer.artineersemina232.entity.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserAccount extends User {
    
    private final Account account;

    public UserAccount(Account account) {
        super(account.getUsername(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account; //필드 변수에 넣어줌.
    }
}
