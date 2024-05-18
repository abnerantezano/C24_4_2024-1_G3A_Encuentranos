package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.repositorios.IServicioRepositorio;
//ESTAMOS CREANDO EL SERVICIO PARA Servicio
@Service
public class ServicioServicio {
	
	//INSTANCIAR LAS CLASES QUE USAREMOS

    @Autowired
    IServicioRepositorio servicioRepositorio;
    
    //OBTENER TODOS LOS SERVICIOS
    public ArrayList<ServicioModelo> obtenerServicios(){
    	return (ArrayList<ServicioModelo>)servicioRepositorio.findAll();
    }
    
    //BUSCAR UN SERVICIO POR ID
    public Optional<ServicioModelo> obtenerServicioPorId(Long id){
    	return servicioRepositorio.findById(id);
    }
}
