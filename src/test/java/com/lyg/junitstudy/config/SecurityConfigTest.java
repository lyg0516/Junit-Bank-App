package com.lyg.junitstudy.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc // Mock(가짜) 환경에 MockMvc가 등록됨
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SecurityConfigTest {

    // 가짜 환경에 등록된 MockMvc를 DI
    @Autowired
    private MockMvc mvc;

    // 서버는 일관성있게 에러가 리턴되어야 한다.
    // 내가 모르는 에러가 프론트한테 날라가지 않게, 내가 직접 다 제어하자.
    @Test
    public void authenticationTest() throws Exception{
        // given

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/api/s/hello")
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        int status = resultActions.andReturn().getResponse().getStatus();
        // then
        assertThat(status).isEqualTo(401);
    }

    @Test
    public void authorizationTest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/api/admin/hello")
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        int status = resultActions.andReturn().getResponse().getStatus();
        // then
    }

}