package com.lyg.junitstudy.handler;

import com.lyg.junitstudy.ex.CustomApiException;
import com.lyg.junitstudy.dto.ResponseDto;
import com.lyg.junitstudy.ex.CustomValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException customApiException){
        log.error(customApiException.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, customApiException.getMessage(),null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> apiException(CustomValidationException customValidationException){
        log.error(customValidationException.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, customValidationException.getMessage(),customValidationException.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

}
