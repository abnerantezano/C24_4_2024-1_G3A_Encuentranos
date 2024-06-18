package com.proyecto.encuentranos.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.servicios.ProveedorServicio;

//ESTAMOS CREANDO EL CONTROLADOR PARA Proveedor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/proveedor")
public class ProveedorControlador {
	
	//INSTANCIAR LAS CLASES QUE USAREMOS
	private final ProveedorServicio proveedorServicio;

	@Autowired
	public ProveedorControlador(ProveedorServicio proveedorServicio) {
		this.proveedorServicio = proveedorServicio;
	}

	//AGREGAR PROVEEDOR
	@PostMapping("/agregar")
	public ResponseEntity<ProveedorModelo> guardarProveedor(@RequestBody ProveedorModelo proveedor) {
		ProveedorModelo nuevoProveedor = this.proveedorServicio.guardarProveedor(proveedor);
		return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED);
	}
	
	//LISTAR PROVEEDORES
	@GetMapping("/listar")
	public ResponseEntity<List<ProveedorModelo>> obtenerProveedor(){
		List<ProveedorModelo> proveedores = this.proveedorServicio.obtenerProveedores();
		return new ResponseEntity<>(proveedores, HttpStatus.OK);
	}
	
	//ACTUALIZAR PROVEEDOR
	@PutMapping(path="/actualizar/{id}")
	public ResponseEntity<ProveedorModelo> actualizarProveedor(@RequestBody ProveedorModelo request, @PathVariable("id") Integer id) {
		ProveedorModelo proveedorActualizado = this.proveedorServicio.actualizarProveedor(id, request);
		return new ResponseEntity<>(proveedorActualizado, HttpStatus.OK);
	}
	
	//BUSCAR PROVEEDOR POR SU ID
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Optional<ProveedorModelo>> encontrarProveedorPorId(@PathVariable Integer id) {
		Optional<ProveedorModelo> proveedorEncontrado = proveedorServicio.encontrarProveedorPorId(id);
		if (proveedorEncontrado.isPresent()) {
			return new ResponseEntity<>(proveedorEncontrado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//BUSCAR PROVEEDOR POR ID DEL USUARIO
	@GetMapping("/buscar-usuario/{idUsuario}")
	public ResponseEntity<Optional<ProveedorModelo>> encontrarProveedorPorIdUsuario(@PathVariable Integer idUsuario) {
	    Optional<ProveedorModelo> proveedorEncontrado = proveedorServicio.buscarProveedorPorUsuarioId(idUsuario);
	    if (proveedorEncontrado.isPresent()) {
	        return new ResponseEntity<>(proveedorEncontrado, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
}
