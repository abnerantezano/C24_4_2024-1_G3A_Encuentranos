package com.proyecto.encuentranos.controladores;


import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.servicios.ProveedorServicio;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/proveedor")
public class ProveedorControlador {
	@Autowired
	private ProveedorServicio proveedorServicio;
	
	@GetMapping("/listar")
	public ArrayList<ProveedorModelo> obtenerProveedor(){
		return this.proveedorServicio.obtenerProveedores();
	}
	
	@GetMapping("/buscar/{id}")
	public Optional<ProveedorModelo> encontrarProveedorPorId(@PathVariable Long id) {
		return proveedorServicio.encontrarProveedorPorId(id);
	}
	
	@PostMapping("/agregar")
	public ProveedorModelo guardarProveedor(@RequestBody ProveedorModelo proveedor) {
		return this.proveedorServicio.guardarProveedor(proveedor);
	}
	
	@PutMapping(path="/actualizar/{id}")
	public ProveedorModelo actualizarProveedor(@RequestBody ProveedorModelo request, @PathVariable("id") Long id) {
		return this.proveedorServicio.actualizarProveedor(id, request);
	}
}
