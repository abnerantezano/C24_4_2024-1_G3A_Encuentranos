package com.proyecto.encuentranos.auth.config;

import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.repositorios.IUsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    public CustomUserDetailsService(IUsuarioRepositorio usuarioRepositorio){
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioModelo usuario = usuarioRepositorio.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        logger.debug("Usuario encontrado: {}", usuario);
        String role = "ROLE_" + usuario.getIdTipo().getNombre().toUpperCase();
        logger.debug("Asignando rol: {}", role);

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasena())
                .authorities(Collections.singletonList(authority))
                .build();
    }
}
