package com.as.we.make.aswemake;

import com.as.we.make.aswemake.account.controller.AccountController;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import com.as.we.make.aswemake.account.request.AccountCreateRequestDto;
import com.as.we.make.aswemake.account.request.AccountLoginRequestDto;
import com.as.we.make.aswemake.account.response.AccountResponseDto;
import com.as.we.make.aswemake.account.service.AccountService;
import com.as.we.make.aswemake.share.ResponseBody;
import com.as.we.make.aswemake.share.StatusCode;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
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
        AccountCreateRequestDto accountCreateRequestDto = AccountCreateRequestDto.builder()
                .accountEmail("mart@naver.com")
                .accountPwd("mart9999!!!!")
                .authority("mart")
                .build();

        doReturn(new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, accountCreateResponseDto(accountCreateRequestDto)), HttpStatus.OK))
                .when(accountService)
                .createAccount(any(AccountCreateRequestDto.class));

        String accountRequestInfo = new Gson().toJson(accountCreateRequestDto);

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
                .andExpect(jsonPath("$.data.accountEmail").value(accountCreateRequestDto.getAccountEmail()))
                .andExpect(jsonPath("$.data.authority").value(accountCreateRequestDto.getAuthority()));

        System.out.println(resultActionsThen);
    }

    @DisplayName("로그인 Controller 테스트")
    @Test
    void loginAccountTest() throws Exception {
        // given
        AccountLoginRequestDto accountLoginRequestDto = AccountLoginRequestDto.builder()
                .accountEmail("mart@naver.com")
                .accountPwd("mart9999!!!!")
                .build();

        doReturn(new ResponseEntity<>(new ResponseBody(StatusCode.IT_WORK, accountLoginResponseDto(accountLoginRequestDto)), HttpStatus.OK))
                .when(accountService)
                .loginAccount(any(AccountLoginRequestDto.class), any(HttpServletResponse.class));

        String accountLoginRequestInfo = new Gson().toJson(accountLoginRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/awm/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(accountLoginRequestInfo));

        // then
        ResultActions resultActionsThen = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accountEmail").value(accountLoginRequestDto.getAccountEmail()));

        System.out.println(resultActionsThen);
    }


    // 정상적으로 반환되었을 경우의 데이터 Dto
    private AccountResponseDto accountCreateResponseDto(AccountCreateRequestDto accountCreateRequestDto) {
        return AccountResponseDto.builder()
                .accountEmail(accountCreateRequestDto.getAccountEmail())
                .authority(accountCreateRequestDto.getAuthority())
                .build();
    }

    // 로그인 정상 반환 데이터 Dto
    private AccountResponseDto accountLoginResponseDto(AccountLoginRequestDto accountLoginRequestDto) {
        return AccountResponseDto.builder()
                .accountEmail(accountLoginRequestDto.getAccountEmail())
                .authority(accountLoginRequestDto.getAccountPwd())
                .build();
    }
}
