package com.proyecto.encuentranos.controladores;

import org.springframework.web.bind.annotation.RestController;

import com.proyecto.encuentranos.modelos.DistritoModelo;
import com.proyecto.encuentranos.servicios.DistritoServicio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/distrito")
public class DistritoControlador {
	
	@Autowired
	DistritoServicio distritoServicio;
	
	@GetMapping("/listar")
	public ArrayList<DistritoModelo> obtenerDistritos() {
		return this.distritoServicio.obtenerDistritos();
	}
	

}
