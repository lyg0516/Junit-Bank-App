package com.lyg.junitstudy.config.jwt;

import com.lyg.junitstudy.config.auth.LoginUser;
import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class JwtAuthorizationFilterTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void authorizationSuccessTest() throws Exception {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        String token = JwtProcess.create(loginUser);
        System.out.println("token = " + token);

        // when
        ResultActions resultActions = mvc.perform(get("/api/s/hello").header(JwtVO.HEADER, token));

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void authorizationFailTest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/s/hello"));

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    public void authorizationAdminTest() throws Exception {
        // given
        User user = User.builder().id(1L).role(UserEnum.ADMIN).build();
        LoginUser loginUser = new LoginUser(user);
        String token = JwtProcess.create(loginUser);
        System.out.println("token = " + token);

        // when
        ResultActions resultActions = mvc.perform(get("/api/admin/hello").header(JwtVO.HEADER, token));

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void authorizationAdminFailTest() throws Exception {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        String token = JwtProcess.create(loginUser);
        System.out.println("token = " + token);

        // when
        ResultActions resultActions = mvc.perform(get("/api/admin/hello").header(JwtVO.HEADER, token));

        // then
        resultActions.andExpect(status().isForbidden());
    }



}