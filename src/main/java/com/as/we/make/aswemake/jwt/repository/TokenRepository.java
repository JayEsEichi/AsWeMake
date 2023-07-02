package com.as.we.make.aswemake.jwt.repository;


import com.as.we.make.aswemake.jwt.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    /** 토큰이 발급된 account의 id 기준으로 토큰 여부 확인 **/
    Token findByAccountId(Long accountId);

    /** 다시 로그인 하게 되었을 때 기존에 있던 토큰 삭제 **/
    void deleteByAccountId(Long accountId);
}
