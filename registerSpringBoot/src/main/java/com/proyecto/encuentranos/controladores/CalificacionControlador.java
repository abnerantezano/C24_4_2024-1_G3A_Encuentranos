package com.proyecto.encuentranos.controladores;

import com.proyecto.encuentranos.modelos.CalificacionModelo;
import com.proyecto.encuentranos.servicios.CalificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calificacion")
public class CalificacionControlador {

    private final CalificacionServicio calificacionServicio;

    @Autowired
    public CalificacionControlador(CalificacionServicio calificacionServicio) {
        this.calificacionServicio = calificacionServicio;
    }

    @GetMapping
    public ResponseEntity<List<CalificacionModelo>> obtenerTodasCalificaciones() {
        List<CalificacionModelo> calificaciones = calificacionServicio.obtenerCalificaciones();
        return new ResponseEntity<>(calificaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalificacionModelo> obtenerCalificacionPorId(@PathVariable("id") Integer id) {
        Optional<CalificacionModelo> calificacionOptional = calificacionServicio.obtenerCalificacionPorId(id);
        if (calificacionOptional.isPresent()) {
            return new ResponseEntity<>(calificacionOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
