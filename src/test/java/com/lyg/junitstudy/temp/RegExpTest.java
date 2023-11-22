package com.lyg.junitstudy.temp;


import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegExpTest {

    @Test
    public void koreanOnly() throws Exception{
        String value = "낫";
        boolean result = Pattern.matches("([가-힣])+", value);
        System.out.println("result = " + result);
    }

    @Test
    public void noKorean() throws Exception{
        String value = "bd231";
        boolean result = Pattern.matches("^[^ㄱ-ㅎ가-힣]+$", value);
        System.out.println("result = " + result);
    }

    @Test
    public void englishOnly() throws Exception{
        String value = "낫";
        boolean result = Pattern.matches("([a-zA-Z])+", value);
        System.out.println("result = " + result);
    }

    @Test
    public void noEnglish() throws Exception{
        String value = "bd231";
        boolean result = Pattern.matches("^[^a-zA-Z]+$", value);
        System.out.println("result = " + result);
    }

    @Test
    public void englishAndNumericOnly() throws Exception{
        String value = "bd231";
        boolean result = Pattern.matches("[a-zA-Z0-9]+$", value);
        System.out.println("result = " + result);
    }

    @Test
    public void engishOnlyLength2to4() throws Exception{
        String value = "낫";
        boolean result = Pattern.matches("[a-zA-Z]{2,4}", value);
        System.out.println("result = " + result);
    }
    
    @Test
    public void usernameTest() throws Exception{
        String username = "ssar";
        boolean result = Pattern.matches("[a-zA-Z0-9]{2,20}", username);
        System.out.println("result = " + result);
    }

    @Test
    public void fullNameTest() throws Exception{
        String fullName = "ssar";
        boolean result = Pattern.matches("[a-zA-Z가-힣]{1,20}", fullName);
        System.out.println("result = " + result);
    }

    @Test
    public void fullEmail() throws Exception{
        String email = "ssar";
        boolean result = Pattern.matches("[a-zA-Z0-9]", email);
        System.out.println("result = " + result);
    }
}
