package com.artineer.artineersemina232.dto;

import com.artineer.artineersemina232.entity.Account;
import lombok.Data;

@Data
public class AccountDto {

    //    @NotBlank(message = "이름을 입력해주세요.")
//    @Size(min = 3, max = 20, message = "이름은 3자 이상 20자 이하로 입력해주세요.")
    private String username;

    //    @NotBlank(message = "패스워드를 입력해주세요.")
//    @Size(min = 6, message = "패스워드는 최소 6자 이상 입력해주세요.")
    private String password;

    public Account toEntity() { // 빌더 사용
        Account userEntity = Account.builder()
                .username(getUsername())
                .password(getPassword())
                .build();

        return userEntity;
    }
}
