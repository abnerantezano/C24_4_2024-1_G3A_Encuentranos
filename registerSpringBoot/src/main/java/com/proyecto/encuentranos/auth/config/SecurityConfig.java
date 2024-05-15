package com.proyecto.encuentranos.auth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.proyecto.encuentranos.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    private UsuarioServicio usuarioService; // Debes inyectar tu servicio de usuarios

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/**","/login/**").permitAll()
                    .anyRequest().authenticated()
            )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage(frontendUrl) // Personaliza la página de inicio de sesión
                        .defaultSuccessUrl(frontendUrl + "/crearUsuario", true) // URL de redirección después del inicio de sesión exitoso
                        .failureHandler((request, response, exception) -> {
                            String email = request.getParameter("email");
                            if (usuarioService.existsByEmail(email)) { // Verifica si el correo ya existe en la tabla de usuarios
                                response.sendRedirect(frontendUrl + "/formulario");
                            } else if (usuarioService.existsInClienteOrProveedor(email)) { // Verifica si el correo está relacionado con un cliente o proveedor
                                response.sendRedirect(frontendUrl + "/inicio");
                            } else {
                                response.sendRedirect(frontendUrl + "/login/failure"); // Si no existe, redirige a la página de inicio de sesión fallida
                            }
                        })
                )
                .build();
    }


    // Configuración CORS igual que en tu clase CorsConfig
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
