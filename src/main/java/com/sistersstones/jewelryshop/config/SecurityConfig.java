package com.sistersstones.jewelryshop.config;

import com.sistersstones.jewelryshop.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/register", "/css/**", "/js/**", "/images/**", "/jewelry/**").permitAll()
            .requestMatchers("/comments/**", "/likes/**", "/cart/**", "/orders/**").authenticated()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .permitAll()
            .defaultSuccessUrl("/", true) // ✅ після логіну — на головну
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/").permitAll() // ✅ після виходу — теж на головну
        );

    return http.build();
}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
