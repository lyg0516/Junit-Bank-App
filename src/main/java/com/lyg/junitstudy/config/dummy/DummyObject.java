package com.lyg.junitstudy.config.dummy;

import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {
    protected User newUser(String username, String fullName){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode("1234"))
                .email("ssar@nate.com")
                .fullName(fullName)
                .role(UserEnum.CUSTOMER)
                .build();
    }

    protected User newMockUser(Long id, String username, String fullName){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
         return User.builder()
                .id(id)
                .username(username)
                .password(bCryptPasswordEncoder.encode("1234"))
                .email("ssar@nate.com")
                .fullName(fullName)
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
