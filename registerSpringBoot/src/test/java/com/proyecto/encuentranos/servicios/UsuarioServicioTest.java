package com.proyecto.encuentranos.servicios;

import com.proyecto.encuentranos.auth.config.PasswordConfig;
import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.repositorios.IUsuarioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServicioTest {

    private UsuarioServicio usuarioServicio;

    @Mock
    private IUsuarioRepositorio usuarioRepositorio;

    @Mock
    private PasswordConfig passwordEncoder;

    @BeforeEach
    void setUp() {
        usuarioRepositorio = mock(IUsuarioRepositorio.class);
        passwordEncoder = mock(PasswordConfig.class);

        usuarioServicio = new UsuarioServicio(passwordEncoder, usuarioRepositorio, null, null);

        // Configurar el comportamiento esperado del mock passwordEncoder
        when(passwordEncoder.passwordEncoder()).thenReturn(mock(PasswordEncoder.class));
    }


    @Test
    void guardarUsuario() {

        UsuarioModelo usuario = new UsuarioModelo();
        usuario.setCorreo("test@example.com");
        usuario.setContrasena("password123");

        when(passwordEncoder.passwordEncoder().encode(usuario.getContrasena())).thenReturn("hashedPassword");
        when(usuarioRepositorio.save(usuario)).thenReturn(usuario);

        UsuarioModelo usuarioGuardado = usuarioServicio.guardarUsuario(usuario);

        assertNotNull(usuarioGuardado);

        assertEquals("hashedPassword", usuarioGuardado.getContrasena());
    }

    @Test
    void buscarUsuarioPorId() {
        UsuarioModelo usuarioPrueba = new UsuarioModelo();
        usuarioPrueba.setIdUsuario(1);

        when(usuarioRepositorio.findById(1)).thenReturn(Optional.of(usuarioPrueba));

        Optional<UsuarioModelo> usuarioEncontrado = usuarioServicio.buscarUsuarioPorId(1);

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals(1, usuarioEncontrado.get().getIdUsuario());
    }

    @Test
    void buscarUsuarioPorCorreo() {

        String correoPrueba = "test@example.com";

        UsuarioModelo usuarioPrueba = new UsuarioModelo();
        usuarioPrueba.setIdUsuario(1);
        usuarioPrueba.setCorreo(correoPrueba);

        when(usuarioRepositorio.findByCorreo(correoPrueba)).thenReturn(Optional.of(usuarioPrueba));

        Optional<UsuarioModelo> usuarioEncontrado = usuarioServicio.buscarUsuarioPorCorreo(correoPrueba);

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals(correoPrueba, usuarioEncontrado.get().getCorreo());
    }
}
