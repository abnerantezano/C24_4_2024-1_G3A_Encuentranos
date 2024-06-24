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

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioModelo usuario = usuarioRepositorio.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String role = "ROLE_" + usuario.getIdTipo().getNombre().toUpperCase();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasena())
                .authorities(Collections.singletonList(authority))
                .build();
    }
}