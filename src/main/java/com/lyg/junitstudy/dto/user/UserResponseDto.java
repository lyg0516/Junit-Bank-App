package com.lyg.junitstudy.dto.user;

import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .fullName(fullName)
                .role(UserEnum.CUSTOMER)
                .build();
    }

    public UserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getUsername();
    }
}
