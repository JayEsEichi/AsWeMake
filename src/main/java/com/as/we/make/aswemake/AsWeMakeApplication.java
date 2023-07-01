package com.as.we.make.aswemake;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AsWeMakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsWeMakeApplication.class, args);
        log.info("application activate ~~~~~~~~~~~~~~~~~~~`");
    }

}
