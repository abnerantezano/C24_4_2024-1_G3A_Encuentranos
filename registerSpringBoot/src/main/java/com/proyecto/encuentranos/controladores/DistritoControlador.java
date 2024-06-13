package com.proyecto.encuentranos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.DistritoModelo;
import com.proyecto.encuentranos.servicios.DistritoServicio;

import java.util.List;

//ESTAMOS CREANDO EL CONTROLADOR PARA Distrito
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/distrito")
public class DistritoControlador {
	
	//INSTANCIAR LAS CLASES QUE USAREMOS
	private final DistritoServicio distritoServicio;

	@Autowired
	public DistritoControlador(DistritoServicio distritoServicio){
		this.distritoServicio = distritoServicio;
	}
	
	//LISTAR LOS DISTRITOS
	@GetMapping("/listar")
	public ResponseEntity<List<DistritoModelo>> obtenerDistritos() {
		List<DistritoModelo> distritos = distritoServicio.obtenerDistritos();
		return new ResponseEntity<>(distritos, HttpStatus.OK);
	}
}
