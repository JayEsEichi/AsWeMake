package com.as.we.make.aswemake;

import com.as.we.make.aswemake.coupon.domain.Coupon;
import com.as.we.make.aswemake.coupon.repository.CouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@Configuration
@EnableJpaAuditing
@SpringBootApplication
public class AsWeMakeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AsWeMakeApplication.class, args);
        log.info("application activate ~~~~~~~~~~~~~~~~~~~`");
    }

    // 테스트를 위한 임의 데이터 즉시 생성
    @Bean
    public CommandLineRunner run(CouponRepository couponRepository) throws Exception {
        return (String[] args) -> {

            if(couponRepository.findAll().isEmpty()){
                couponRepository.save(
                        Coupon.builder()
                                .couponType("FIX")
                                .discountContent(2000)
                                .build()
                );

                couponRepository.save(
                        Coupon.builder()
                                .couponType("SPECIFIC")
                                .discountContent(0.5F)
                                .build()
                );
            }

        };
    }

}
