package com.twistreview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/reviews/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(basic -> {});
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        return new InMemoryUserDetailsManager(
            User.withUsername("admin").password("adminpass").roles("ADMIN").build(),
            User.withUsername("user1").password("user1pass").roles("USER").build(),
            User.withUsername("user2").password("user2pass").roles("USER").build()
        );
    }
}
