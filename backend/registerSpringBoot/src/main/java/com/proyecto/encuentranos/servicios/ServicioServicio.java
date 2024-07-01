package com.proyecto.encuentranos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.repositorios.IServicioRepositorio;
@Service
public class ServicioServicio {
	
    private final IServicioRepositorio servicioRepositorio;

    @Autowired
    public ServicioServicio(IServicioRepositorio servicioRepositorio) {
        this.servicioRepositorio = servicioRepositorio;
    }

    //OBTENER TODOS LOS SERVICIOS
    public List<ServicioModelo> obtenerServicios(){
    	return servicioRepositorio.findAll();
    }
    
    //BUSCAR UN SERVICIO POR ID
    public Optional<ServicioModelo> obtenerServicioPorId(Integer idServicio){
    	return servicioRepositorio.findById(idServicio);
    }
}
