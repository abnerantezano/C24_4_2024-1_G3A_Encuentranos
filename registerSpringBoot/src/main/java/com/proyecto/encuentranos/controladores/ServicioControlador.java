package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.servicios.ServicioServicio;
//ESTAMOS CREANDO EL CONTROLADOR PARA Servicio
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/servicio")
public class ServicioControlador {
	
	//INSTANCIAR LAS CLASES QUE USAREMOS

	@Autowired
	private ServicioServicio servicioServicio;
	
	//LISTAR SERVICIOS
	@GetMapping("/listar")
	public ArrayList<ServicioModelo> obtenerServicios(){
		return this.servicioServicio.obtenerServicios();
	}
	
	//BUSCAR UN SERVICIO POR SU ID
	@GetMapping("/buscar/{id}")
	public Optional <ServicioModelo> obtenerServicioPorId(Long id){
		return servicioServicio.obtenerServicioPorId(id);
	}

	
}
