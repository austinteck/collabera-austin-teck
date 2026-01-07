package com.collabera.austinteck.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Environment startup configuration logger
 * @author Austin Teck
 */
@Configuration
public class StartupConfig {

    private static final Logger log = LoggerFactory.getLogger(StartupConfig.class);

    /**
     * Profile = dev
     * @return Correct logger for dev profile
     */
    @Bean
    @Profile("dev")
    CommandLineRunner devRunner(){
        return args -> log.info("Application started in dev");
    }

    /**
     * Profile = uat
     * @return  Correct logger for uat profile
     */
    @Bean
    @Profile("uat")
    CommandLineRunner uatRunner(){
        return args -> log.info("Application started in uat");
    }

    /**
     * Profile = prd
     * @return  Correct logger for prd profile
     */
    @Bean
    @Profile("prd")
    CommandLineRunner prdRunner(){
        return args -> log.info("Application started in prd");
    }
}
