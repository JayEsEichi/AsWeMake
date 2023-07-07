package com.as.we.make.aswemake.account.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Account implements UserDetails {

    // 계정 고유 id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long accountId;

    // 계정 이메일
    @Column(nullable = false)
    private String accountEmail;

    // 계정 비밀번호
    @Column(nullable = false)
    private String accountPwd;

    // JWT 관련 권한
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> authority = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.authority.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword(){
        return accountPwd;
    }

    @Override
    public String getUsername(){
        return accountEmail;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
