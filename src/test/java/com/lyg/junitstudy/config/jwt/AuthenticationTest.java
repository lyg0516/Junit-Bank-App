package com.lyg.junitstudy.config.jwt;

import com.lyg.junitstudy.config.auth.LoginUser;
import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationTest {
    @Test
    public void create_test() throws  Exception {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);

        // when
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("jwtToken = " + jwtToken);

        // then
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    public void verify_test() throws Exception{
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);

        String jwtToken = JwtProcess.create(loginUser).replace(JwtVO.TOKEN_PREFIX, "");
        System.out.println("jwtToken = " + jwtToken);
        // when
        LoginUser verify = JwtProcess.verify(jwtToken);

        // then
        Assertions.assertThat(verify.getUser().getId()).isEqualTo(1L);
    }
}
