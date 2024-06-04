package com.proyecto.encuentranos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.CalificacionModelo;
import com.proyecto.encuentranos.modelos.DetalleCalificacionModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.repositorios.ICalificacionRepositorio;
import com.proyecto.encuentranos.repositorios.IDetalleCalificacionRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;

@Service
public class CalificacionServicio {

    @Autowired
    private ICalificacionRepositorio calificacionRepositorio;

    @Autowired
    private IDetalleCalificacionRepositorio detalleCalificacionRepositorio;

    @Autowired
    private IProveedorRepositorio proveedorRepositorio;

    public List<CalificacionModelo> obtenerCalificaciones() {
        return calificacionRepositorio.findAll();
    }


    public Optional<CalificacionModelo> obtenerCalificacionPorId(Integer id) {
        return calificacionRepositorio.findById(id);
    }
}
