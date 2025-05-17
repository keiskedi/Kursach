package com.sistersstones.jewelryshop.config;

import com.sistersstones.jewelryshop.security.CustomAuthenticationSuccessHandler;
import com.sistersstones.jewelryshop.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // 🔐 Головна конфігурація безпеки
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // можна включити, якщо буде CSRF-токен у формі

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/register", "/login", "/css/**", "/images/**").permitAll() // відкриті маршрути
                .requestMatchers("/admin/**").hasRole("ADMIN") // захищено для ADMIN
                .anyRequest().authenticated() // все інше — тільки після входу
            )

            .formLogin(form -> form
                .loginPage("/login") // сторінка логіну (HTML-форма)
                .loginProcessingUrl("/login") // куди надсилається форма
                .successHandler(authenticationSuccessHandler()) // обробка після успішного входу
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/") // вихід — на головну
                .permitAll()
            );

        return http.build();
    }

    // 🔑 Обробник успішного логіну (перенаправлення за роллю)
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    // 🔐 Менеджер аутентифікації
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 🔐 Енкодер для паролів
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
