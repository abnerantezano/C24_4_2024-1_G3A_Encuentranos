package com.proyecto.encuentranos.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    @Autowired
    public SecurityConfig(CorsConfigurationSource corsConfigurationSource,
                          AuthenticationSuccessHandler successHandler,
                          AuthenticationFailureHandler failureHandler) {
        this.corsConfigurationSource = corsConfigurationSource;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Value("${frontend.url}") String frontendUrl) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**", "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage(frontendUrl)
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                )
                .build();
    }
}
