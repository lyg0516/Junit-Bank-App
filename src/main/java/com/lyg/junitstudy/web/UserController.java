package com.lyg.junitstudy.web;

import com.lyg.junitstudy.dto.ResponseDto;
import com.lyg.junitstudy.dto.user.UserRequestDto;
import com.lyg.junitstudy.dto.user.UserResponseDto;
import com.lyg.junitstudy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;
    
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult){

        UserResponseDto joinResponseDto = userService.join(userRequestDto);
        return new ResponseEntity<>(new ResponseDto(1, "회원가입 성공", joinResponseDto), HttpStatus.CREATED);
    }
}
