package com.proyecto.encuentranos.auth.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.proyecto.encuentranos.servicios.UsuarioServicio;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioServicio usuarioService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Value("${frontend.url}") String frontendUrl) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/**","/login/**").permitAll()
                    .anyRequest().authenticated()
            )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage(frontendUrl) // Personaliza la página de inicio de sesión
                        .successHandler(successHandler(frontendUrl))
                        .failureHandler(failureHandler(frontendUrl))
                )
                .build();
    }

    private AuthenticationSuccessHandler successHandler(String frontendUrl) {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    			// Obtener el usuario autenticado
    			OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
    			OAuth2User oauth2User = oauthToken.getPrincipal();
    			
    			// Obtener los atributos del usuario
    			String email = (String) oauth2User.getAttribute("email");
                System.out.println("Holas " + email);
                if (usuarioService.existsByEmail(email)) {
                    if (usuarioService.existsInClienteOrProveedor(email)) {
                        response.sendRedirect(frontendUrl + "/inicio");
                    } else {
                        response.sendRedirect(frontendUrl + "/formulario");
                    }
                } else {
                    response.sendRedirect(frontendUrl + "/crearUsuario");
                }
            }
        };
    }

    private AuthenticationFailureHandler failureHandler(String frontendUrl) {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                String email = request.getParameter("correo");
                System.out.println("Hola " + email);
                response.sendRedirect(frontendUrl + "/crearUsuario");
            }
        };
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://transcendent-starburst-8b45b4.netlify.app"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}