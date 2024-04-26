package com.proyecto.encuentranos.controllers;

import java.util.List;

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

    @GetMapping("/{id}")
    public PrestadorModel obtenerPrestadorConUsuarioPorId(@PathVariable Long id) {
        return prestadorServicio.obtenerPrestadorConUsuarioPorId(id);
    }
}

