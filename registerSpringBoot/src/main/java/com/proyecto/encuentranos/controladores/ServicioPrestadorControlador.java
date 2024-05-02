package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.ServicioPrestadorModelo;
import com.proyecto.encuentranos.servicios.ServicioPrestadorServicio;

@RestController
@RequestMapping("/servicio-prestador")
public class ServicioPrestadorControlador {
	
	@Autowired
	ServicioPrestadorServicio servicioPrestadorServicio;
	
	@GetMapping("/listar")
	public ArrayList<ServicioPrestadorModelo> obtenerServiciosPrestadores(){
		return this.servicioPrestadorServicio.obtenerServiciosPrestadores();
	}
	
	@GetMapping("/buscar/{id}")
	public Optional <ServicioPrestadorModelo> obtenerServicioPrestadorPorId(@PathVariable Long id){
		return this.servicioPrestadorServicio.obtenerServicioPrestadorPorId(id);
	}
	
	@PostMapping("/agregar")
	public ServicioPrestadorModelo agregarServicioPrestador(@RequestBody ServicioPrestadorModelo servicioPrestador) {
		return this.servicioPrestadorServicio.agregarServicioPrestador(servicioPrestador);
	}
	
	@PutMapping(path="/actualizar/{id}")
	public ServicioPrestadorModelo actualizarServicioPrestador(@RequestBody ServicioPrestadorModelo servicioPrestador, @PathVariable Long id) {
		return this.servicioPrestadorServicio.actualizarServicioPrestador(id, servicioPrestador);
	}

}
