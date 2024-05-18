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
//ESTAMOS CREANDO EL CONTROLADOR PARA Proveedor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/proveedor")
public class ProveedorControlador {
	
	//INSTANCIAR LAS CLASES QUE USAREMOS

	@Autowired
	private ProveedorServicio proveedorServicio;
	
	//AGREGAR PROVEEDOR
	@PostMapping("/agregar")
	public ProveedorModelo guardarProveedor(@RequestBody ProveedorModelo proveedor) {
		return this.proveedorServicio.guardarProveedor(proveedor);
	}
	
	//LISTAR PROVEEDORES
	@GetMapping("/listar")
	public ArrayList<ProveedorModelo> obtenerProveedor(){
		return this.proveedorServicio.obtenerProveedores();
	}
	
	//ACTUALIZAR PROVEEDOR
	@PutMapping(path="/actualizar/{id}")
	public ProveedorModelo actualizarProveedor(@RequestBody ProveedorModelo request, @PathVariable("id") Integer id) {
		return this.proveedorServicio.actualizarProveedor(id, request);
	}
	
	//BUSCAR PROVEEDOR POR SU ID
	@GetMapping("/buscar/{id}")
	public Optional<ProveedorModelo> encontrarProveedorPorId(@PathVariable Integer id) {
		return proveedorServicio.encontrarProveedorPorId(id);
	}
}
