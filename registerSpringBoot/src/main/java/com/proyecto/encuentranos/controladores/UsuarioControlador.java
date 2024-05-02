package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.servicios.UsuarioServicio;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {
	
	@Autowired
	UsuarioServicio usuarioServicio;
	
	@GetMapping("/listar")
	public ArrayList<UsuarioModelo> obtenerUsuarios(){
		return this.usuarioServicio.obtenerUsuarios();
	}
	
	@PostMapping("/agregar")
	public UsuarioModelo guardarUsuario(@RequestBody UsuarioModelo request) {
		return this.usuarioServicio.guardarUsuario(request);
	}

}
