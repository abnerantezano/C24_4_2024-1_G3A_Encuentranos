package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.repositorios.IServicioRepositorio;
@Service
public class ServicioServicio {
    @Autowired
    IServicioRepositorio servicioRepositorio;
    
    public ArrayList<ServicioModelo> obtenerServicios(){
    	return (ArrayList<ServicioModelo>)servicioRepositorio.findAll();
    }
    
    public Optional<ServicioModelo> obtenerServicioPorId(Long id){
    	return servicioRepositorio.findById(id);
    }
}
