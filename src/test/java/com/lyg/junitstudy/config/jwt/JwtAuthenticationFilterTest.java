package com.lyg.junitstudy.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyg.junitstudy.config.auth.LoginUser;
import com.lyg.junitstudy.config.dummy.DummyObject;
import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserEnum;
import com.lyg.junitstudy.domain.user.UserRepository;
import com.lyg.junitstudy.dto.user.LoginRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class JwtAuthenticationFilterTest extends DummyObject {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() throws Exception{
        userRepository.save(newUser("ssar", "ssar"));
    }

    @Test
    public void successfulAuthenticationTest() throws Exception{

        // given
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("ssar");
        loginRequestDto.setPassword("1234");
        String requestBody = om.writeValueAsString(loginRequestDto);


        // when
        ResultActions resultActions = mvc.perform(post("/api/login").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
        String header = resultActions.andReturn().getResponse().getHeader(JwtVO.HEADER);

        // then
        resultActions.andExpect(status().isOk());
        assertNotNull(header);
        assertTrue(header.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    public void unSuccessfulAuthenticationTest() throws Exception{
        // given
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("ssar");
        loginRequestDto.setPassword("123");
        String requestBody = om.writeValueAsString(loginRequestDto);


        // when
        ResultActions resultActions = mvc.perform(post("/api/login").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
        String header = resultActions.andReturn().getResponse().getHeader(JwtVO.HEADER);


        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}