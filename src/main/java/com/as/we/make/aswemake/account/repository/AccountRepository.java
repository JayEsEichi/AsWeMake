package com.as.we.make.aswemake.account.repository;

import com.as.we.make.aswemake.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountEmail(String accountEmail);
}
