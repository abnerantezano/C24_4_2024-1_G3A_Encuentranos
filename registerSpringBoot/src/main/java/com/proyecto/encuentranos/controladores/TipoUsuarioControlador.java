package com.proyecto.encuentranos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.servicios.TipoUsuarioServicio;

import java.util.ArrayList;
//ESTAMOS CREANDO EL CONTROLADOR PARA TipoUsuario
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/tipo-usuario")
public class TipoUsuarioControlador {
    
	//INSTANCIAR LAS CLASES QUE USAREMOS

    @Autowired
    TipoUsuarioServicio tipoUsuarioServicio;
   
    //LISTARR TIPO DE USUARIOS
    @GetMapping("/listar")
    public ArrayList<TipoUsuarioModelo> encontrarTipoUsuarios(){
    	return (ArrayList<TipoUsuarioModelo>)tipoUsuarioServicio.encontrarTipoUsuarios();
    }

}
