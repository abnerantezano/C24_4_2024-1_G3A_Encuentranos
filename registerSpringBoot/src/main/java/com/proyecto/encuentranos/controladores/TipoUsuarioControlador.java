package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.servicios.TipoUsuarioServicio;
<<<<<<< Updated upstream
@CrossOrigin(origins = "http://localhost:3000")
=======

@CrossOrigin(origins = "http://localhost:3000/")
>>>>>>> Stashed changes
@RestController
@RequestMapping("/tipo-usuario")
public class TipoUsuarioControlador {
	@Autowired
	TipoUsuarioServicio tipoUsuarioControlador;
	
	@GetMapping("/listar")
	public ArrayList<TipoUsuarioModelo> obtenerTipoUsuario(){
		return this.tipoUsuarioControlador.obtenerTipoUsuarios();
	}
	
}
