package com.lyg.junitstudy.dto.user;

import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
    // 영문, 숫자는 되고, 길이 최소 2~20자 이내
    @Pattern(regexp = "^[a-zA-Z0-9]{2,20}", message = "영문/숫자 2~20자 이내로 작성해주세요.")
    @NotEmpty // null이거나 공백일 수 없다.
    private String username;

    // 길이 4~20
    @NotEmpty
    @Size(min=4, max = 20)
    private String password;

    // 이메일 형식
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "email형식으로 작성해주세요.")
    private String email;

    // 영어, 한글, 1~2
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}", message = "한글/영문 1~20자 이내로 작성해주세요")
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

}
