package com.as.we.make.aswemake.account;

import com.as.we.make.aswemake.account.domain.Account;
import com.as.we.make.aswemake.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Account account = accountRepository.findByAccountEmail(email).orElseThrow(() -> new NullPointerException("존재하지 않는 계정입니다."));

        return createUserDetails(account);
    }

    private UserDetails createUserDetails(Account account) {

        log.info("createUserDetails 실행");

        return User.builder()
                .username(account.getAccountEmail())
                .password(account.getAccountPwd())
                .roles(account.getAuthority().toArray(new String[0]))
                .build();
    }
}
