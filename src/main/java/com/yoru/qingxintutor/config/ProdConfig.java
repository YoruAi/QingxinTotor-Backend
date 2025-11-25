package com.yoru.qingxintutor.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService dummyUserDetailsService() {
        return username -> {
            throw new RuntimeException("Not used in prod");
        };
    }
}
