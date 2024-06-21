package com.proyecto.encuentranos.auth.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.proyecto.encuentranos.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioServicio usuarioService;

    @Autowired
    public SecurityConfig(UsuarioServicio usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Value("${frontend.url}") String frontendUrl) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login/**", "/**").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                    .loginPage(frontendUrl)
                    .successHandler(successHandler(frontendUrl))
                    .failureHandler(failureHandler(frontendUrl))
            )
            .build();
    }

    private AuthenticationSuccessHandler successHandler(String frontendUrl) {
        return (request, response, authentication) -> {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute("email"); // Obtener el correo electrónico del usuario

            String idToken = obtenerIdTokenFirebase(oauth2User); // Obtener el token JWT para Firebase

            // Aquí deberías enviar el idToken a Firebase para autenticar la solicitud
            try {
                FirebaseAuth.getInstance().verifyIdToken(idToken);
            } catch (FirebaseAuthException e) {
                e.printStackTrace();
                // Manejar errores al enviar el token a Firebase
                response.sendRedirect(frontendUrl + "/error");
                return;
            }

            // Redirigir al usuario a la página correspondiente
            if (usuarioService.existsByEmail(email)) {
                if (usuarioService.existsInClienteOrProveedor(email)) {
                    response.sendRedirect(frontendUrl + "/inicio");
                } else {
                    response.sendRedirect(frontendUrl + "/formulario");
                }
            } else {
                response.sendRedirect(frontendUrl + "/crearUsuario");
            }
        };
    }


    private AuthenticationFailureHandler failureHandler(String frontendUrl) {
        return (request, response, exception) -> response.sendRedirect(frontendUrl + "/crearUsuario");
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://transcendent-starburst-8b45b4.netlify.app", "http://10.0.2.2", "http://localhost"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private String obtenerIdTokenFirebase(OAuth2User oauth2User) {
        return (String) oauth2User.getAttribute("firebase_token");
    }
}
