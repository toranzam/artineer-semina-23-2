package com.artineer.artineersemina232.service;

import com.artineer.artineersemina232.dto.UserDto;
import com.artineer.artineersemina232.entity.UserEntity;
import com.artineer.artineersemina232.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;  //파이널이 없으면 객체에 값이 안 들어감.

    private final PasswordEncoder passwordEncoder;

    public UserEntity saveNewUser(UserDto userDto) {
        log.info(userDto.toString());
        System.out.println(userDto);

        // 엔티티로 변환.
        UserEntity userEntity = userDto.toEntity();

        // 비밀번호 인코딩 암호화.
        String rawPassword = userEntity.getPassword();
        //passwardEntity를 통해 rawPassward를 인코딩.
        String encodePassword = passwordEncoder.encode(rawPassword);

        userEntity.setPassword(encodePassword); //인코딩된 비밀번호로 교체
        log.info(userEntity.toString());


        UserEntity newUser = userRepository.save(userEntity); // DB에 저장
        log.info("회원가입완료");
        return newUser;
    }
}
