package com.lyg.junitstudy.service;

import com.lyg.junitstudy.config.dummy.DummyObject;
import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserEnum;
import com.lyg.junitstudy.domain.user.UserRepository;
import com.lyg.junitstudy.dto.user.UserRequestDto;
import com.lyg.junitstudy.dto.user.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// spring 관련 Bean들이 하나도 없는 환경
@ExtendWith(MockitoExtension.class)
class UserServiceTest extends DummyObject {

    @InjectMocks  // 가짜 객체를 주입받는다
    private UserService userService;

    @Mock  // 가짜 객체
    private UserRepository userRepository;

    @Spy  // Mock에서 spring 객체를 주입할 때 사용
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void joinTest() throws Exception{
        // given
        UserRequestDto joinRequestDto = new UserRequestDto();
        joinRequestDto.setUsername("ssar");
        joinRequestDto.setPassword("1234");
        joinRequestDto.setEmail("ssar@nate.com");
        joinRequestDto.setFullName("쌀");

        // stub1
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        // stub2
        User ssar = newMockUser(1L, "ssar", "쌀");
        when(userRepository.save(any())).thenReturn(ssar);

        // when
        UserResponseDto join = userService.join(joinRequestDto);
        System.out.println("join = " + join);
        // then
        assertThat(join.getId()).isEqualTo(1L);
        assertThat(join.getUsername()).isEqualTo("ssar");
    }
}