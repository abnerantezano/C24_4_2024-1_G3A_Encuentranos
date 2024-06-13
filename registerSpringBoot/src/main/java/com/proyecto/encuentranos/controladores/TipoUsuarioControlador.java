package com.proyecto.encuentranos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.servicios.TipoUsuarioServicio;

import java.util.List;

//ESTAMOS CREANDO EL CONTROLADOR PARA TipoUsuario
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/tipo-usuario")
public class TipoUsuarioControlador {
    
    //INSTANCIAR LAS CLASES QUE USAREMOS

    private final TipoUsuarioServicio tipoUsuarioServicio;

    @Autowired
    public TipoUsuarioControlador(TipoUsuarioServicio tipoUsuarioServicio) {
        this.tipoUsuarioServicio = tipoUsuarioServicio;
    }

    //LISTAR TIPO DE USUARIOS
    @GetMapping("/listar")
    public ResponseEntity<List<TipoUsuarioModelo>> encontrarTipoUsuarios() {
        List<TipoUsuarioModelo> tiposUsuarios = tipoUsuarioServicio.encontrarTipoUsuarios();
        if (!tiposUsuarios.isEmpty()) {
            return new ResponseEntity<>(tiposUsuarios, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
