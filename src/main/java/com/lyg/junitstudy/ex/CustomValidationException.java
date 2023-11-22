package com.lyg.junitstudy.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{

    private Map<String ,String> errorMap;

    public Map<String, String> getErrorMap() {
        return errorMap;
    }

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }
}
