package com.proyecto.encuentranos.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.models.PrestadorServicioModel;
import com.proyecto.encuentranos.repositories.IPrestadorServicioRepositorio;

@Service
public class PrestadorServicioServicio {
	@Autowired
	IPrestadorServicioRepositorio prestadorServicioRepositorio;
	
	@Autowired
	PrestadorServicio prestadorServicio;
	
	public ArrayList<PrestadorServicioModel> obtenerPrestadorServicio(){
		return (ArrayList<PrestadorServicioModel>)prestadorServicioRepositorio.findAll();
	}
	

    public PrestadorServicioModel obtenerPrestadorConServicio(Long id) {
        Optional<PrestadorServicioModel> optionalPrestadorServicio = prestadorServicioRepositorio.findPrestadorServicioById(id);
        return optionalPrestadorServicio.orElse(null);
    }
    
    public PrestadorServicioModel guardarPrestadorServicio(PrestadorServicioModel prestador) {
    	return prestadorServicioRepositorio.save(prestador);
    }
    
    public PrestadorServicioModel actualizarPrestadorServicio(Long id, PrestadorServicioModel prestadorActualizado) {
        PrestadorServicioModel prestadorExistente = prestadorServicioRepositorio.findById(id).orElse(null);

        if (prestadorExistente != null) {
            // Actualizar datos del prestador de servicio
            prestadorExistente.setPrecio(prestadorActualizado.getPrecio());
            
            // Actualizar datos del prestador asociado
            prestadorExistente.getPrestador().getUsuario().setNombre(prestadorActualizado.getPrestador().getUsuario().getNombre());
            prestadorExistente.getPrestador().getUsuario().setApellidoPaterno(prestadorActualizado.getPrestador().getUsuario().getApellidoPaterno());
            prestadorExistente.getPrestador().getUsuario().setApellidoMaterno(prestadorActualizado.getPrestador().getUsuario().getApellidoMaterno());
            prestadorExistente.getPrestador().getUsuario().setDni(prestadorActualizado.getPrestador().getUsuario().getDni());
            prestadorExistente.getPrestador().getUsuario().setCelular(prestadorActualizado.getPrestador().getUsuario().getCelular());
            prestadorExistente.getPrestador().getUsuario().setCorreoElectronico(prestadorActualizado.getPrestador().getUsuario().getCorreoElectronico());
            prestadorExistente.getPrestador().getUsuario().setContrasena(prestadorActualizado.getPrestador().getUsuario().getContrasena());

            // Actualizar dirección del prestador asociado
            prestadorExistente.getPrestador().getUsuario().getDirecciones().get(0).setRegion(prestadorActualizado.getPrestador().getUsuario().getDirecciones().get(0).getRegion());
            prestadorExistente.getPrestador().getUsuario().getDirecciones().get(0).setProvincia(prestadorActualizado.getPrestador().getUsuario().getDirecciones().get(0).getProvincia());
            prestadorExistente.getPrestador().getUsuario().getDirecciones().get(0).setDistrito(prestadorActualizado.getPrestador().getUsuario().getDirecciones().get(0).getDistrito());

            // Guardar el prestador de servicio actualizado
            prestadorExistente = prestadorServicioRepositorio.save(prestadorExistente);
        }
        return prestadorExistente;
    }
	
	
}
