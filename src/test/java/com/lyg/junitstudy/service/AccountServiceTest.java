package com.lyg.junitstudy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyg.junitstudy.config.dummy.DummyObject;
import com.lyg.junitstudy.domain.account.Account;
import com.lyg.junitstudy.domain.account.AccountRepository;
import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserRepository;
import com.lyg.junitstudy.dto.account.AccountSaveReqDto;
import com.lyg.junitstudy.dto.account.AccountSaveRespDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest extends DummyObject {

    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks // 모든 Mock들이 InjectMocks로 주입 됨
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void createAccountTest() throws Exception {
        // given
        Long userId = 1L;

        AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
        accountSaveReqDto.setNumber(1111L);
        accountSaveReqDto.setPassword(1234L);

        // stub1
        User ssar = newMockUser(userId, "ssar", "쌀");
        when(userRepository.findById(any())).thenReturn(Optional.of(ssar));

        // stub2
        when(accountRepository.findByNumber(any())).thenReturn(Optional.empty());

        // stub3
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);
        when(accountRepository.save(any())).thenReturn(ssarAccount);

        // when
        AccountSaveRespDto accountSaveRespDto = accountService.registryAccount(accountSaveReqDto, userId);
        String responseBody = objectMapper.writeValueAsString(accountSaveRespDto);
        System.out.println("responseBody = " + responseBody);

        // then
        assertThat(accountSaveRespDto.getNumber()).isEqualTo(1111L);
    }
}