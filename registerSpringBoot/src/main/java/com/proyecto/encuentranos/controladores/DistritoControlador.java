package com.proyecto.encuentranos.controladores;

import org.springframework.web.bind.annotation.RestController;

import com.proyecto.encuentranos.modelos.DistritoModelo;
import com.proyecto.encuentranos.servicios.DistritoServicio;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public ArrayList<DistritoModelo> obtenerDistritos() {
		return this.distritoServicio.obtenerDistritos();
	}
	
}
