package com.proyecto.encuentranos.auth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/", "/public/**").permitAll()
				.anyRequest().authenticated())
			.oauth2Login(Customizer.withDefaults())
			.logout(logout -> logout
	                .logoutSuccessUrl("/")
	                .permitAll())
			.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/error")); // Manejar el error 403 redirigiendo a /error
		
		return http.build();
	}
}

