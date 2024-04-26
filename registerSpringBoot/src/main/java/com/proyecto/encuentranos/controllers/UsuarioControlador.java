package com.proyecto.encuentranos.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.models.UsuarioModel;
import com.proyecto.encuentranos.services.UsuarioServicio;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@GetMapping
	public ArrayList<UsuarioModel> obtenerUsuario(){
		return this.usuarioServicio.obtenerUsuarios();
	}
	
	@PostMapping
	public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuario) {
		return this.usuarioServicio.guardarUsuario(usuario);
	}
	
	@GetMapping(path = "/{id}")
	public Optional <UsuarioModel> obtenerUsuarioPorId(@PathVariable("id") Long id){
		return this.usuarioServicio.obtenerUsuarioPorId(id);
	}
}
