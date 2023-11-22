package com.lyg.junitstudy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyg.junitstudy.dto.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomResponseUtil {

    public static void unAuthentication(HttpServletResponse response, Integer status, String message){

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(-1, message, null);
            String responseBody = null;
            responseBody = objectMapper.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(status);
            response.getWriter().println(responseBody);  // 예쁘게 메시지를 포장하는 공통적인 응답 DTO생성
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }

    }
}
