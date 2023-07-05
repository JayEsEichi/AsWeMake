package com.as.we.make.aswemake.account.repository;

import com.as.we.make.aswemake.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /** 이메일로 계정 조회 **/
    Optional<Account> findByAccountEmail(String accountEmail);

    /** 계정 고유 id 로 계정 조회 **/
    Optional<Account> findByAccountId(Long accountId);

}
