package com.lyg.junitstudy.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserEnum;
import com.lyg.junitstudy.domain.user.UserRepository;
import com.lyg.junitstudy.dto.user.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup(){
        dataSetting();
    }

    @Test
    public void joinTest() throws Exception {
        // given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("love");
        userRequestDto.setPassword("1234");
        userRequestDto.setEmail("love@nate.com");
        userRequestDto.setFullName("러브");
        System.out.println("userRequestDto = " + userRequestDto);
        String requestBody = om.writeValueAsString(userRequestDto);
        // when
        ResultActions resultActions = mvc.perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void joinFailTest() throws Exception {
        // given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("ssar");
        userRequestDto.setPassword("1234");
        userRequestDto.setEmail("love@nate.com");
        userRequestDto.setFullName("러브");
        System.out.println("userRequestDto = " + userRequestDto);
        String requestBody = om.writeValueAsString(userRequestDto);
        // when
        ResultActions resultActions = mvc.perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
        // then
        resultActions.andExpect(status().isBadRequest());
    }

    private void dataSetting() {
        userRepository.save(new User().builder()
                .username("ssar").fullName("쌀").
                email("123@123.com").password("1234").
                role(UserEnum.CUSTOMER).
                build());
    }

}
