package com.proyecto.encuentranos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.servicios.TipoUsuarioServicio;
import com.proyecto.encuentranos.servicios.UsuarioServicio;

import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/login")
public class LoginControlador {

    @Autowired
    UsuarioServicio usuarioServicio;
    
    @Autowired
    TipoUsuarioServicio tipoUsuarioServicio;

    
    @PostMapping("/autenticar")
    public ResponseEntity<String> autenticarUsuario(@RequestBody UsuarioModelo usuario) {
        Optional<UsuarioModelo> usuarioEncontrado = usuarioServicio.buscarUsuarioPorCorreo(usuario.getCorreo());
        
        if(usuarioEncontrado.isPresent() && usuarioEncontrado.get().getContrasena().equals(usuario.getContrasena())) {
            return new ResponseEntity<>("Usuario autenticado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Correo o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }
    }
   
}