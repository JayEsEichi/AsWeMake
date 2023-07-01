package com.as.we.make.aswemake;

import com.as.we.make.aswemake.account.controller.AccountController;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.account.request.AccountRequestDto;
import com.as.we.make.aswemake.account.response.AccountResponseDto;
import com.as.we.make.aswemake.account.service.AccountService;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @DisplayName("계정 생성 Controller 테스트")
    @Test
    void createAccountTest() throws Exception {
        // given
        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .accountEmail("mart@naver.com")
                .accountPwd("mart9999!!!!")
                .authority("mart")
                .build();

        doReturn(new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, accountResponseDto(accountRequestDto)), HttpStatus.OK))
                .when(accountService)
                .createAccount(any(AccountRequestDto.class));

        String accountRequestInfo = new Gson().toJson(accountRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/awm/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(accountRequestInfo));

        // then
        ResultActions resultActionsThen = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accountEmail").value(accountRequestDto.getAccountEmail()))
                .andExpect(jsonPath("$.data.authority").value(accountRequestDto.getAuthority()));

        System.out.println(resultActionsThen);
    }


    // 정사적으로 반환되었을 경우의 데이터 Dto
    private AccountResponseDto accountResponseDto(AccountRequestDto accountRequestDto) {
        return AccountResponseDto.builder()
                .accountEmail(accountRequestDto.getAccountEmail())
                .authority(accountRequestDto.getAuthority())
                .build();
    }
}
