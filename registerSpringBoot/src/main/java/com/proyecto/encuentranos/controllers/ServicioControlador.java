package com.proyecto.encuentranos.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.encuentranos.models.ServicioModel;
import com.proyecto.encuentranos.services.ServicioServicio;

@RestController
@RequestMapping("/servicio")
public class ServicioControlador {
	@Autowired
	private ServicioServicio servicioServicio;
	
	@GetMapping
	public ArrayList<ServicioModel> obtenerServicios(){
		return this.servicioServicio.obtenerServicios();
	}
	
	@GetMapping("/{id}")
	public Optional<ServicioModel> obtenerServicioPorId(@PathVariable Long id){
		return servicioServicio.obtenerServicioPorId(id);
	}
}
