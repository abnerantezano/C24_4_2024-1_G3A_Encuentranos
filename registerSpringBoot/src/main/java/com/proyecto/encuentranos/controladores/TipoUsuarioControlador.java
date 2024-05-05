package com.proyecto.encuentranos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.servicios.TipoUsuarioServicio;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/tipo-usuario")
public class TipoUsuarioControlador {
    
    @Autowired
    TipoUsuarioServicio tipoUsuarioServicio;
   
    @GetMapping("/listar")
    public ResponseEntity<List<TipoUsuarioModelo>> encontrarTipoUsuarios(){
    	List<TipoUsuarioModelo> tipoUsuarios = this.tipoUsuarioServicio.encontrarTipoUsuarios();
        return new ResponseEntity<>(tipoUsuarios, HttpStatus.OK);
    }
}
