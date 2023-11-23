package com.lyg.junitstudy.dto.user;

import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.util.CustomDateUtil;
import lombok.Data;

@Data
public class LoginResponseDto {

    private Long id;
    private String username;
    private String createAt;

    public LoginResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.createAt = CustomDateUtil.toStringFormat(user.getCreatedAt());
    }
}
