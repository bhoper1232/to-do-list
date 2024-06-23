package com.bhoper.config;

import com.bhoper.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

}
