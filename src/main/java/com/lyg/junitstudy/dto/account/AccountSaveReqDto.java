package com.lyg.junitstudy.dto.account;

import com.lyg.junitstudy.domain.account.Account;
import com.lyg.junitstudy.domain.user.User;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSaveReqDto{
    @NotNull
    @Digits(integer = 4, fraction = 4)
    private Long number;

    @NotNull
    @Digits(integer = 4, fraction = 4)
    private Long password;

    public Account toEntity(User user){
        return Account.builder()
                .number(number)
                .password(password)
                .balance(1000L)
                .user(user)
                .build();
    }
}