package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.repositorios.IUsuarioRepositorio;

@Service
public class UsuarioServicio {

	@Autowired
	IUsuarioRepositorio usuarioRepositorio;
	
    public ArrayList<UsuarioModelo> obtenerUsuarios(){
    	return (ArrayList<UsuarioModelo>)usuarioRepositorio.findAll();
    }
	public UsuarioModelo guardarUsuario(UsuarioModelo usuario) {
		return usuarioRepositorio.save(usuario);
	}
}
