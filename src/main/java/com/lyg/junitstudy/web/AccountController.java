package com.lyg.junitstudy.web;

import com.lyg.junitstudy.config.auth.LoginUser;
import com.lyg.junitstudy.dto.ResponseDto;
import com.lyg.junitstudy.dto.account.AccountSaveReqDto;
import com.lyg.junitstudy.dto.account.AccountSaveRespDto;
import com.lyg.junitstudy.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/s/account")
    public ResponseEntity<?> saveAccount(@RequestBody @Valid AccountSaveReqDto accountSaveReqDto, BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser){
        AccountSaveRespDto accountSaveRespDto = accountService.registryAccount(accountSaveReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌등록 성공", accountSaveRespDto), HttpStatus.CREATED);
    }
}
