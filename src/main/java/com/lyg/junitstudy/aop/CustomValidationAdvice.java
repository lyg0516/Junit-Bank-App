package com.lyg.junitstudy.aop;

import com.lyg.junitstudy.dto.ResponseDto;
import com.lyg.junitstudy.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CustomValidationAdvice {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){

    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping(){

    }

    @Around("postMapping() || putMapping()")
    public Object validationAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs(); // joinPoint의 매개변수
        for (Object arg : args) {
            if(arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg;
                if(bindingResult.hasErrors()){
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError fieldError : bindingResult.getFieldErrors()) {
                        errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패", errorMap);
                }
            }
        }
        return joinPoint.proceed(); // 정상적으로 해당 메서드를 실행해라!
    }
}


