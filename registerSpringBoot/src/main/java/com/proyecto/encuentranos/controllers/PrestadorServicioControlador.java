package com.proyecto.encuentranos.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.encuentranos.models.PrestadorServicioModel;
import com.proyecto.encuentranos.models.UsuarioModel;
import com.proyecto.encuentranos.services.PrestadorServicioServicio;

@RestController
@RequestMapping("/prestador-servicio")
public class PrestadorServicioControlador {

    @Autowired
    private PrestadorServicioServicio prestadorServicioServicio;

    @GetMapping
    public ArrayList<PrestadorServicioModel> obtenerPrestadorServicio() {
    	return prestadorServicioServicio.obtenerPrestadorServicio();
    }
    
    @PostMapping
    public PrestadorServicioModel guardarPrestadorServicio(@RequestBody PrestadorServicioModel prestadorServicio) {
        return prestadorServicioServicio.guardarPrestadorServicio(prestadorServicio);
    }
    
    @GetMapping(path = "/{id}")
    public PrestadorServicioModel obtenerPrestadorConServicio(@PathVariable Long id) {
        return prestadorServicioServicio.obtenerPrestadorConServicio(id);
    }
    
    @PutMapping(path = "/{id}")
    public PrestadorServicioModel actualizarPrestadorServicio(@RequestBody PrestadorServicioModel request, @PathVariable("id") Long id) {
    	return this.prestadorServicioServicio.actualizarPrestadorServicio(id, request);
    }
}
