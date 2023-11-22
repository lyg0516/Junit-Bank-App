package com.lyg.junitstudy.service;

import com.lyg.junitstudy.ex.CustomApiException;
import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserRepository;
import com.lyg.junitstudy.dto.user.UserRequestDto;
import com.lyg.junitstudy.dto.user.UserResponseDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    // 서비스는 DTO를 받고, DTO를 출력한다.
    @Transactional // 트랜잭션이 메서드 시작할 때 시작되고, 종료될 때 종료된다.
    public UserResponseDto join(UserRequestDto joinRequestDto){
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinRequestDto.getUsername());
        if(userOP.isPresent()){
            // 유저네임 중복
            throw new CustomApiException("동일한 username이 존재합니다.");
        }

        // 2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinRequestDto.toEntity(bCryptPasswordEncoder));

        // 3. dto 응답
        return new UserResponseDto(userPS);
    }





}
