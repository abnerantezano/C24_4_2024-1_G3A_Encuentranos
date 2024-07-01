package com.proyecto.encuentranos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.CalificacionModelo;
import com.proyecto.encuentranos.repositorios.ICalificacionRepositorio;

@Service
public class CalificacionServicio {

    private final ICalificacionRepositorio calificacionRepositorio;

    @Autowired
    public CalificacionServicio(ICalificacionRepositorio calificacionRepositorio) {
        this.calificacionRepositorio = calificacionRepositorio;
    }

    public List<CalificacionModelo> obtenerCalificaciones() {
        return calificacionRepositorio.findAll();
    }

    public CalificacionModelo agregarCalificacion(CalificacionModelo calificacionModelo){
        return calificacionRepositorio.save(calificacionModelo);
    }

    public Optional<CalificacionModelo> obtenerCalificacionPorId(Integer id) {
        return calificacionRepositorio.findById(id);
    }
}
