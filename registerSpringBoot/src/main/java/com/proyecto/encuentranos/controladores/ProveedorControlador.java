package com.proyecto.encuentranos.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.servicios.ProveedorServicio;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@PreAuthorize("hasRole('PROVEEDOR')")
@RequestMapping("/proveedor")
public class ProveedorControlador {
	
	private final ProveedorServicio proveedorServicio;

	@Autowired
	public ProveedorControlador(ProveedorServicio proveedorServicio) {
		this.proveedorServicio = proveedorServicio;
	}

	@PostMapping("/agregar")
	public ResponseEntity<ProveedorModelo> guardarProveedor(@RequestBody ProveedorModelo proveedor) {
		ProveedorModelo nuevoProveedor = this.proveedorServicio.guardarProveedor(proveedor);
		return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED);
	}

	@GetMapping("/listar")
	public ResponseEntity<List<ProveedorModelo>> obtenerProveedor(){
		List<ProveedorModelo> proveedores = this.proveedorServicio.obtenerProveedores();
		return new ResponseEntity<>(proveedores, HttpStatus.OK);
	}
	
	@PutMapping(path="/actualizar/{id}")
	public ResponseEntity<ProveedorModelo> actualizarProveedor(@RequestBody ProveedorModelo request, @PathVariable("id") Integer id) {
		ProveedorModelo proveedorActualizado = this.proveedorServicio.actualizarProveedor(id, request);
		return new ResponseEntity<>(proveedorActualizado, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Optional<ProveedorModelo>> encontrarProveedorPorId(@PathVariable Integer id) {
		Optional<ProveedorModelo> proveedorEncontrado = proveedorServicio.encontrarProveedorPorId(id);
		if (proveedorEncontrado.isPresent()) {
			return new ResponseEntity<>(proveedorEncontrado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
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
