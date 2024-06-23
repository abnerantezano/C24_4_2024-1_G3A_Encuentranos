package com.proyecto.encuentranos.auth.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.proyecto.encuentranos.servicios.UsuarioServicio;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class CustomAuthenticationHandlers {

    private final UsuarioServicio usuarioService;

    @Autowired
    public CustomAuthenticationHandlers(UsuarioServicio usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(@Value("${frontend.url}") String frontendUrl) {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                OAuth2User oauth2User = oauthToken.getPrincipal();

                String email = (String) oauth2User.getAttribute("email");
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

    @Bean
    public AuthenticationFailureHandler failureHandler(@Value("${frontend.url}") String frontendUrl) {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                response.sendRedirect(frontendUrl + "/crearUsuario");
            }
        };
    }
}
