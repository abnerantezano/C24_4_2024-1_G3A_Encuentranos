package com.proyecto.encuentranos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.models.ClienteModel;
import com.proyecto.encuentranos.models.DireccionModel;
import com.proyecto.encuentranos.models.PrestadorModel;
import com.proyecto.encuentranos.models.UsuarioModel;
import com.proyecto.encuentranos.repositories.IDireccionRepositorio;
import com.proyecto.encuentranos.repositories.IPrestadorRepositorio;
import com.proyecto.encuentranos.repositories.IUsuarioRepositorio;

@Service
public class PrestadorServicio {

    @Autowired
    IPrestadorRepositorio prestadorRepositorio;

    
    @Autowired
    IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    IDireccionRepositorio direccionRepositorio;

    public ArrayList<PrestadorModel> obtenerPrestadoresConUsuario() {
        return (ArrayList<PrestadorModel>)prestadorRepositorio.findAll();
    }

    public PrestadorModel guardarPrestador(PrestadorModel prestador) {
        return prestadorRepositorio.save(prestador);
    }

    public PrestadorModel actualizarPrestador(Long id, PrestadorModel prestadorActualizado) {
        PrestadorModel prestadorExistente = prestadorRepositorio.findById(id).orElse(null);

        if (prestadorExistente != null) {
            // Actualizar datos del usuario
            prestadorExistente.getUsuario().setNombre(prestadorActualizado.getUsuario().getNombre());
            prestadorExistente.getUsuario().setApellidoPaterno(prestadorActualizado.getUsuario().getApellidoPaterno());
            prestadorExistente.getUsuario().setApellidoMaterno(prestadorActualizado.getUsuario().getApellidoMaterno());
            prestadorExistente.getUsuario().setDni(prestadorActualizado.getUsuario().getDni());
            prestadorExistente.getUsuario().setCelular(prestadorActualizado.getUsuario().getCelular());
            prestadorExistente.getUsuario().setCorreoElectronico(prestadorActualizado.getUsuario().getCorreoElectronico());
            prestadorExistente.getUsuario().setContrasena(prestadorActualizado.getUsuario().getContrasena());

            // Actualizar dirección del usuario
            prestadorExistente.getUsuario().getDirecciones().get(0).setRegion(prestadorActualizado.getUsuario().getDirecciones().get(0).getRegion());
            prestadorExistente.getUsuario().getDirecciones().get(0).setProvincia(prestadorActualizado.getUsuario().getDirecciones().get(0).getProvincia());
            prestadorExistente.getUsuario().getDirecciones().get(0).setDistrito(prestadorActualizado.getUsuario().getDirecciones().get(0).getDistrito());

            // Guardar el prestador actualizado
            prestadorExistente = prestadorRepositorio.save(prestadorExistente);
        }
        return prestadorExistente;
    }
    
    public Optional <PrestadorModel> obtenerPrestadorPorId(Long id) {
    	return prestadorRepositorio.findById(id);
    }

}

