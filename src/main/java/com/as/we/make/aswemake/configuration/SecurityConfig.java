package com.as.we.make.aswemake.configuration;


import com.as.we.make.aswemake.jwt.JwtAuthenticationFilter;
import com.as.we.make.aswemake.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    // Spring Security 적용 후 토큰 인증이 필요하지 않은 경로 허용 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic((basic) -> basic.disable())
                .csrf((csrf) -> csrf.disable())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/awm/account/**",
                                "/awm/order/calculate",
                                "/awm/product/get",
                                "/v3/api-docs/**",
                                "/swagger-ui/**").permitAll()
                        // product api 경로에는 MART 권한을 가진 계정만이 접근가능하게 하는 옵션.
                        // 하지만 USER 권한을 가진 계정이 접근했을 경우의 예외처리를 확인하기 위해 주석처리
                        // .requestMatchers("/awm/product/**").hasAuthority("MART")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 비밃번호 인코딩 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}