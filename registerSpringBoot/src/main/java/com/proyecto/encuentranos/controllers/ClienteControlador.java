package com.proyecto.encuentranos.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.encuentranos.models.ClienteModel;
import com.proyecto.encuentranos.models.PrestadorModel;
import com.proyecto.encuentranos.services.ClienteServicio;

@RestController
@RequestMapping("/cliente")
public class ClienteControlador {
	
	@Autowired
	private ClienteServicio clienteServicio;
	
	@GetMapping
	public ArrayList<ClienteModel> obtenerCliente(){
		return this.clienteServicio.obtenerCliente();
	}
	
	@PostMapping
	public ClienteModel guardarCliente(@RequestBody ClienteModel cliente) {
		return this.clienteServicio.guardarCliente(cliente);
	}
	
	@PutMapping(path = "/{id}")
	public ClienteModel actualizarCliente(@RequestBody ClienteModel request, @PathVariable("id") Long id) {
		return this.clienteServicio.actualizarCliente(id, request);
	}
	
    @GetMapping("/{id}")
    public Optional<ClienteModel> obtenerClientePorId(@PathVariable Long id) {
        return clienteServicio.obtenerClientePorId(id);
    }
}
