package com.proyecto.encuentranos.auth.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	
	@Value("${frontend.url}")
	private String frontendUrl;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
        		.csrf(AbstractHttpConfigurer::disable)
        		.cors(cors -> cors.configurationSource(corsConfiguration()))
        		.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/cliente/**").permitAll()
            .anyRequest().authenticated();
        })
        .oauth2Login(oath2 -> {
            oath2.successHandler(oAuth2LoginSuccessHandler);
        })      
        .build();
    }
    
    @Bean
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource UrlBasedCorsConfigurationSourceSource = new UrlBasedCorsConfigurationSource();
        UrlBasedCorsConfigurationSourceSource.registerCorsConfiguration("/**", configuration);
        
        return UrlBasedCorsConfigurationSourceSource;
    }
}
