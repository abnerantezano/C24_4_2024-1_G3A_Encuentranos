package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ServicioPrestadorModelo;
import com.proyecto.encuentranos.repositorios.*;

@Service
public class ServicioPrestadorServicio {
	@Autowired
	IServicioPrestadorRepositorio servicioPrestadorRepositorio;
	
	@Autowired
	IServicioRepositorio servicioRepositorion;
	
	@Autowired
	IProveedorRepositorio proveedorRepositorio;
	
	public ArrayList<ServicioPrestadorModelo> obtenerServiciosPrestadores(){
		return (ArrayList<ServicioPrestadorModelo>)servicioPrestadorRepositorio.findAll();
	}
	
	
	public Optional<ServicioPrestadorModelo> obtenerServicioPrestadorPorId(Long id){
		return servicioPrestadorRepositorio.findById(id);
	}
	
	public ServicioPrestadorModelo agregarServicioPrestador(ServicioPrestadorModelo servicioPrestador) {
		return servicioPrestadorRepositorio.save(servicioPrestador);
	}
	
	public ServicioPrestadorModelo actualizarServicioPrestador(Long id, ServicioPrestadorModelo servicioPrestador) {
		ServicioPrestadorModelo servicioPrestadorExistente = servicioPrestadorRepositorio.findById(id).orElse(null);
		
		if(servicioPrestadorExistente != null) {
			//Actualizar datos del ServicioPrestador
			servicioPrestadorExistente.setIdProveedor(servicioPrestador.getIdProveedor());
			servicioPrestadorExistente.setIdServicio(servicioPrestador.getIdServicio());
			servicioPrestadorExistente.setPrecio(servicioPrestador.getPrecio());
			
			servicioPrestadorExistente = servicioPrestadorRepositorio.save(servicioPrestadorExistente);
		}
		return servicioPrestadorExistente;
	}
	
}
