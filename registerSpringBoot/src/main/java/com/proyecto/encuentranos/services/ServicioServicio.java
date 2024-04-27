package com.proyecto.encuentranos.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.models.ServicioModel;
import com.proyecto.encuentranos.repositories.IServicioRepositorio;

@Service
public class ServicioServicio {

    @Autowired
    IServicioRepositorio servicioRepositorio;
    
    public ArrayList<ServicioModel> obtenerServicios(){
    	return (ArrayList<ServicioModel>)servicioRepositorio.findAll();
    }
    
    public Optional<ServicioModel> obtenerServicioPorId(Long id){
    	return servicioRepositorio.findById(id);
    }
}
