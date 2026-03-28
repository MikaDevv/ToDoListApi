package com.mika.tasks.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev") // This configuration is only active when the "dev" profile is used
public class DevelopmentSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection, common for development
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // Allow all requests

        return http.build();
    }
}
