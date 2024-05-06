package com.proyecto.encuentranos.controladores;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.servicios.ClienteServicio;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/cliente")
public class ClienteControlador {
	@Autowired
	private ClienteServicio clienteServicio;
	
	@GetMapping("/listar")
	public ArrayList<ClienteModelo> obtenerCliente(){
		return this.clienteServicio.obtenerClientes();
	}
	
	@GetMapping("/buscar/{id}")
	public Optional<ClienteModelo> encontrarClientePorId(@PathVariable Long id) {
		return clienteServicio.encontrarClientePorId(id);
	}
	
	@PostMapping("/agregar")
	public ClienteModelo guardarCliente(@RequestBody ClienteModelo cliente) {
		return this.clienteServicio.guardarCliente(cliente);
	}
	
	@PutMapping(path="/actualizar/{id}")
	public ClienteModelo actualizarCliente(@RequestBody ClienteModelo request, @PathVariable("id") Long id) {
		return this.clienteServicio.actualizarCliente(id, request);
	}
	
	@GetMapping("/buscar-proveedores/{idCliente}")
	public List<ProveedorModelo> buscarProveedoresPorDistrito(@PathVariable Long idCliente) {
		return clienteServicio.encontrarPrestadoresDeMiDistrito(idCliente);
	}

}
