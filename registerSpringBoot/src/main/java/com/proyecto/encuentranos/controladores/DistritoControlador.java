package com.proyecto.encuentranos.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.DistritoModelo;
import com.proyecto.encuentranos.servicios.DistritoServicio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

//ESTAMOS CREANDO EL CONTROLADOR PARA Distrito
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/distrito")
public class DistritoControlador {
	
	//INSTANCIAR LAS CLASES QUE USAREMOS

	@Autowired
	DistritoServicio distritoServicio;
	
	//LISTAR LOS DISTRITOS
	@GetMapping("/listar")
	public ResponseEntity<ArrayList<DistritoModelo>> obtenerDistritos() {
		ArrayList<DistritoModelo> distritos = distritoServicio.obtenerDistritos();
		return new ResponseEntity<>(distritos, HttpStatus.OK);
	}
}
