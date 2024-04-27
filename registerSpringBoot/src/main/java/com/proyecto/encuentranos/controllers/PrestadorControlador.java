package com.proyecto.encuentranos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.models.PrestadorModel;
import com.proyecto.encuentranos.services.PrestadorServicio;

@RestController
@RequestMapping("/prestador")
public class PrestadorControlador {

    @Autowired
    private PrestadorServicio prestadorServicio;

    @GetMapping
    public List<PrestadorModel> obtenerPrestadores() {
        return prestadorServicio.obtenerPrestadoresConUsuario();
    }

    @PostMapping
    public PrestadorModel guardarPrestador(@RequestBody PrestadorModel prestador) {
        return prestadorServicio.guardarPrestador(prestador);
    }
    
    @PutMapping(path = "/{id}")
    public PrestadorModel actualizarPrestador(@RequestBody PrestadorModel request, @PathVariable("id") Long id) {
    	return this.prestadorServicio.actualizarPrestador(id, request);
    }

    @GetMapping("/{id}")
    public Optional<PrestadorModel> obtenerPrestadorPorId(@PathVariable Long id) {
        return prestadorServicio.obtenerPrestadorPorId(id);
    }
}

