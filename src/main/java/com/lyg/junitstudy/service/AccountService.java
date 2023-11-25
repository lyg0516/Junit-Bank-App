package com.lyg.junitstudy.service;

import com.lyg.junitstudy.domain.account.Account;
import com.lyg.junitstudy.domain.account.AccountRepository;
import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserRepository;
import com.lyg.junitstudy.dto.ResponseDto;
import com.lyg.junitstudy.dto.account.AccountSaveReqDto;
import com.lyg.junitstudy.dto.account.AccountSaveRespDto;
import com.lyg.junitstudy.ex.CustomApiException;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    public AccountSaveRespDto registryAccount(AccountSaveReqDto accountSaveReqDto, Long userId){
        // 유저가 로그인이 되어 있어야 한다.
        // 체크는 서비스의 역할이 아니라 컨트롤러의 역할

        // User가 DB에 있는지 검증
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("유저를 찾을 수 없습니다.")
        );

        // 해당 계과자 DB에 있는지 중복여부를 체크
        Optional<Account> accountOP = accountRepository.findByNumber(accountSaveReqDto.getNumber());
        if(accountOP.isPresent()){
            throw new CustomApiException("해당 계좌가 이미 존재합니다.");
        }
        // 계좌 등록
        Account accountPS = accountRepository.save(accountSaveReqDto.toEntity(user));

        // DTO를 응답
        return new AccountSaveRespDto(accountPS);
    }




}

