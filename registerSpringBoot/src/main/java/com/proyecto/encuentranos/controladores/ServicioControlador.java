package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.servicios.ServicioServicio;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/servicio")
public class ServicioControlador {
	@Autowired
	private ServicioServicio servicioServicio;
	
	@GetMapping("/listar")
	public ArrayList<ServicioModelo> obtenerServicios(){
		return this.servicioServicio.obtenerServicios();
	}
	
	@GetMapping("/buscar/{id}")
	public Optional <ServicioModelo> obtenerServicioPorId(Long id){
		return servicioServicio.obtenerServicioPorId(id);
	}

	
}
