package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ArrayList<ServicioModelo>> obtenerServicios(){
		ArrayList<ServicioModelo> servicios = this.servicioServicio.obtenerServicios();
		return new ResponseEntity<>(servicios, HttpStatus.OK);
	}
	
	//BUSCAR UN SERVICIO POR SU ID
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Optional<ServicioModelo>> obtenerServicioPorId(@PathVariable Integer id){
		Optional<ServicioModelo> servicioEncontrado = servicioServicio.obtenerServicioPorId(id);
		if (servicioEncontrado.isPresent()) {
			return new ResponseEntity<>(servicioEncontrado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
